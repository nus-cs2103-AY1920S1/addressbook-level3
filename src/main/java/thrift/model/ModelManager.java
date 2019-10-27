package thrift.model;

import static java.util.Objects.requireNonNull;
import static thrift.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import thrift.commons.core.GuiSettings;
import thrift.commons.core.LogsCenter;
import thrift.commons.core.index.Index;
import thrift.commons.util.CollectionUtil;
import thrift.logic.commands.Undoable;
import thrift.logic.commands.exceptions.CommandException;
import thrift.model.transaction.Budget;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;
import thrift.model.transaction.TransactionIsInMonthYearPredicate;

/**
 * Represents the in-memory model of the THRIFT data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Thrift thrift;
    private final UserPrefs userPrefs;
    private final FilteredList<Transaction> filteredTransactions;
    private final PastUndoableCommands pastUndoableCommands;
    private final Calendar currentMonthYear;
    private double balance;
    private double expense;
    private double income;

    /** {@code Predicate} that always show the current month transactions */
    private Predicate<Transaction> predicateShowCurrentMonthTransactions;

    /**
     * Initializes a ModelManager with the given thrift, userPrefs and pastUndoableCommands.
     */
    public ModelManager(ReadOnlyThrift thrift, ReadOnlyUserPrefs userPrefs, PastUndoableCommands pastUndoableCommands) {
        super();
        requireAllNonNull(thrift, userPrefs);

        logger.fine("Initializing with THRIFT: " + thrift + " and user prefs " + userPrefs);

        this.thrift = new Thrift(thrift);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTransactions = new FilteredList<>(this.thrift.getTransactionList());
        this.pastUndoableCommands = pastUndoableCommands;
        currentMonthYear = Calendar.getInstance();
        balance = 0;
        expense = 0;
        predicateShowCurrentMonthTransactions = new TransactionIsInMonthYearPredicate(currentMonthYear);
    }

    public ModelManager() {
        this(new Thrift(), new UserPrefs(), new PastUndoableCommands());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getThriftFilePath() {
        return userPrefs.getThriftFilePath();
    }

    @Override
    public void setThriftFilePath(Path thriftFilePath) {
        requireNonNull(thriftFilePath);
        userPrefs.setThriftFilePath(thriftFilePath);
    }

    //=========== THRIFT ================================================================================

    @Override
    public void setThrift(ReadOnlyThrift thrift) {
        this.thrift.resetData(thrift);
    }

    @Override
    public ReadOnlyThrift getThrift() {
        return thrift;
    }

    @Override
    public boolean hasTransaction(Transaction t) {
        requireNonNull(t);
        return thrift.hasTransaction(t);
    }

    @Override
    public Optional<Index> getIndexInFullTransactionList(Transaction transaction) {
        return thrift.getTransactionIndex(transaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        thrift.removeTransaction(transaction);
    }

    @Override
    public void deleteTransaction(Index index) {
        thrift.removeTransactionByIndex(index);
    }

    @Override
    public Transaction deleteLastTransaction() {
        return thrift.removeLastTransaction();
    }

    @Override
    public void addExpense(Expense expense) {
        thrift.addTransaction(expense);
    }

    @Override
    public void addExpense(Expense expense, Index index) {
        thrift.addTransaction(expense, index);
    }

    @Override
    public void addIncome(Income income) {
        thrift.addTransaction(income);
    }

    @Override
    public void addIncome(Income income, Index index) {
        thrift.addTransaction(income, index);
    }

    @Override
    public String getCurrentMonthYear() {
        return new SimpleDateFormat("MMMMM yyyy").format(currentMonthYear.getTime());
    }

    @Override
    public double getCurrentMonthBudget() {
        Optional<Budget> optBudget = thrift.getBudgetList().getBudgetForMonthYear(currentMonthYear);
        if (optBudget.isPresent()) {
            return optBudget.get().getBudgetValue().getMonetaryValue();
        } else {
            return 0;
        }
    }

    @Override
    public Optional<Budget> setBudget(Budget budget) {
        requireNonNull(budget);
        return thrift.setBudget(budget);
    }

    @Override
    public void resetBudgetForThatMonth(Budget budget) {
        requireNonNull(budget);
        thrift.removeBudget(budget);
    }

    @Override
    public void setTransaction(Transaction target, Transaction updatedTransaction) {
        CollectionUtil.requireAllNonNull(target, updatedTransaction);
        thrift.setTransaction(target, updatedTransaction);
        updateBalanceForCurrentMonth();
    }

    @Override
    public void setTransactionWithIndex(Index actualIndex, Transaction updatedTransaction) {
        CollectionUtil.requireAllNonNull(actualIndex, updatedTransaction);
        thrift.setTransactionWithIndex(actualIndex, updatedTransaction);
        updateBalanceForCurrentMonth();
    }

    //=========== Filtered Transaction List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Transaction} backed by the internal list of
     * {@code versionedThrift}
     */
    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return filteredTransactions;
    }

    /** Filters the view of the transaction list to only show transactions that occur in the current month. */
    @Override
    public void updateFilteredTransactionListToCurrentMonth() {
        filteredTransactions.setPredicate(predicateShowCurrentMonthTransactions);
        updateBalanceForCurrentMonth();
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public void updateBalanceForCurrentMonth() {
        //If transaction does not belong to current displayed month, don't update the balance.
        logger.info("Original balance: " + balance);
        balance = getCurrentMonthBudget() + thrift.getTransactionList().stream()
                .filter(t -> {
                    Calendar temp = Calendar.getInstance();
                    temp.setTime(t.getDate().getDate());
                    if (temp.get(Calendar.MONTH) != currentMonthYear.get(Calendar.MONTH)
                            || temp.get(Calendar.YEAR) != currentMonthYear.get(Calendar.YEAR)) {
                        return false;
                    }
                    return true;
                })
                .mapToDouble(t -> {
                    double value = t.getValue().getMonetaryValue();
                    if (t instanceof Expense) {
                        return value * -1;
                    }
                    return value;
                })
                .sum();
        logger.info("Updated balance: " + balance);
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void updateExpenseForCurrentMonth() {
        //If transaction does not belong to current displayed month and not an expense, don't update the expense.
        logger.info("Original expense: " + expense);
        expense = thrift.getTransactionList().stream()
                .filter(t -> {
                    Calendar temp = Calendar.getInstance();
                    temp.setTime(t.getDate().getDate());
                    if (temp.get(Calendar.MONTH) != currentMonthYear.get(Calendar.MONTH)
                            || temp.get(Calendar.YEAR) != currentMonthYear.get(Calendar.YEAR)
                            || t instanceof Income) {
                        return false;
                    }
                    return true;
                })
                .mapToDouble(t -> t.getValue().getMonetaryValue())
                .sum();
        logger.info("Updated expense: " + expense);
    }

    @Override
    public double getExpense() {
        return expense;
    }

    @Override
    public void updateIncomeForCurrentMonth() {
        //If transaction does not belong to current displayed month and not an income, don't update the income.
        logger.info("Original income: " + income);
        income = thrift.getTransactionList().stream()
                .filter(t -> {
                    Calendar temp = Calendar.getInstance();
                    temp.setTime(t.getDate().getDate());
                    if (temp.get(Calendar.MONTH) != currentMonthYear.get(Calendar.MONTH)
                            || temp.get(Calendar.YEAR) != currentMonthYear.get(Calendar.YEAR)
                            || t instanceof Expense) {
                        return false;
                    }
                    return true;
                })
                .mapToDouble(t -> t.getValue().getMonetaryValue())
                .sum();
        logger.info("Updated income: " + income);
    }

    @Override
    public double getIncome() {
        return income;
    }

    @Override
    public boolean isInView(Transaction transaction) {
        return filteredTransactions.contains(transaction);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return thrift.equals(other.thrift)
                && userPrefs.equals(other.userPrefs)
                && filteredTransactions.equals(other.filteredTransactions);
    }

    //=========== Past Commands History =============================================================
    @Override
    public void keepTrackCommands(Undoable command) {
        pastUndoableCommands.addPastCommand(command);
    }

    @Override
    public Undoable getPreviousUndoableCommand() throws CommandException {
        return pastUndoableCommands.getCommandToUndo();
    }

    @Override
    public boolean hasUndoableCommand() {
        return pastUndoableCommands.hasUndoCommand();
    }

    @Override
    public Undoable getUndoneCommand() throws CommandException {
        return pastUndoableCommands.getCommandToRedo();
    }

    @Override
    public boolean hasUndoneCommand() {
        return pastUndoableCommands.hasRedoCommand();
    }
}
