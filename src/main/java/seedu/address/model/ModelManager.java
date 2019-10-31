package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final VersionedBankAccount versionedUserState;
    private final FilteredList<BankAccountOperation> filteredTransactions;
    private final FilteredList<Budget> filteredBudgets;
    private final FilteredList<LedgerOperation> filteredLedgerOperations;

    /**
     * Initializes a ModelManager with the given bankAccount and userPrefs.
     */
    public ModelManager(ReadOnlyBankAccount bankAccount, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(bankAccount, userPrefs);

        logger.fine("Initializing with bank account" + bankAccount + " and user prefs " + userPrefs);

        this.versionedUserState = new VersionedBankAccount(bankAccount);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredTransactions = new FilteredList<>(this.versionedUserState.getTransactionHistory());
        filteredBudgets = new FilteredList<>(this.versionedUserState.getBudgetHistory());
        filteredLedgerOperations = new FilteredList<>(this.versionedUserState.getLedgerHistory());
    }

    public ModelManager() {
        this(new BankAccount(), new UserPrefs());
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

    //=========== Bank Account =============================================================

    @Override
    public Path getBankAccountFilePath() {
        return userPrefs.getBankAccountFilePath();
    }

    @Override
    public void setBankAccountFilePath(Path bankAccountFilePath) {
        requireNonNull(bankAccountFilePath);
        userPrefs.setBankAccountFilePath(bankAccountFilePath);
    }

    @Override
    public void setBankAccount(ReadOnlyBankAccount bankAccount) {
        requireNonNull(bankAccount);
        this.versionedUserState.resetData(bankAccount);
    }

    @Override
    public ReadOnlyBankAccount getBankAccount() {
        return versionedUserState;
    }

    @Override
    public boolean hasTransaction(BankAccountOperation transaction) {
        requireNonNull(transaction);
        return versionedUserState.hasTransaction(transaction);
    }

    @Override
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return versionedUserState.hasBudget(budget);
    }

    @Override
    public void deleteTransaction(BankAccountOperation transaction) {
        versionedUserState.removeTransaction(transaction);
    }

    @Override
    public void deleteBudget(Budget budget) {
        versionedUserState.removeBudget(budget);
    }

    @Override
    public void setTransaction(BankAccountOperation transactionTarget, BankAccountOperation transactionEdit) {
        requireAllNonNull(transactionTarget, transactionEdit);

        versionedUserState.setTransaction(transactionTarget, transactionEdit);
    }

    @Override
    public void setBudget(Budget budgetTarget, Budget budgetEdit) {
        requireAllNonNull(budgetTarget, budgetEdit);

        versionedUserState.setBudget(budgetTarget, budgetEdit);
    }

    @Override
    public void addTransaction(BankAccountOperation transaction) {
        versionedUserState.addTransaction(transaction);
    }

    @Override
    public void addBudget(Budget budget) {
        versionedUserState.addBudget(budget);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     * @return
     */
    @Override
    public FilteredList<BankAccountOperation> getFilteredTransactionList() {
        return filteredTransactions;
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudgets;
    }

    @Override
    public ObservableList<LedgerOperation> getFilteredLedgerOperationsList() {
        return filteredLedgerOperations;
    }

    @Override
    public boolean canUndoBankAccount() {
        return versionedUserState.canUndo();
    }

    @Override
    public void undoBankAccount() {
        versionedUserState.undo();
    }

    @Override
    public boolean canRedoBankAccount() {
        return versionedUserState.canRedo();
    }

    @Override
    public void redoBankAccount() {
        versionedUserState.redo();
    }

    @Override
    public void commitBankAccount() {
        versionedUserState.commit();
    }

    @Override
    public void setTransactions(List<BankAccountOperation> transactionHistory) {
        versionedUserState.setTransactions(transactionHistory);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<BankAccountOperation> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }

    @Override
    public void handleOperation(BankAccountOperation operation) {
        versionedUserState.addTransaction(operation);
    }

    @Override
    public void handleOperation(LedgerOperation operation) {
        versionedUserState.addLoan(operation);
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
        return versionedUserState.equals(other.versionedUserState)
                && userPrefs.equals(other.userPrefs)
                && filteredTransactions.equals(other.filteredTransactions)
                && filteredBudgets.equals(other.filteredBudgets);
    }

}
