package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.budget.ReadOnlyBudgetList;
import seedu.address.model.expense.Expense;

/**
 * Represents the in-memory model of the expense list data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final ExpenseList expenseList;
    private final BudgetList budgetList;
    private final UserPrefs userPrefs;
    private final FilteredList<Expense> defaultFilteredExpenses;
    private final FilteredList<Budget> filteredBudgets;
    private FilteredList<Expense> filteredExpenses;

    /**
     * Initializes a ModelManager with the given expenseList, budgetlist and userPrefs.
     */
    public ModelManager(ReadOnlyExpenseList expenseList, ReadOnlyBudgetList budgetList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseList, userPrefs);

        logger.fine("Initializing with expense list: " + expenseList + ", user prefs " + userPrefs
            + " budget list: " + budgetList);

        this.expenseList = new ExpenseList(expenseList);
        this.budgetList = new BudgetList(budgetList);
        this.userPrefs = new UserPrefs(userPrefs);
        defaultFilteredExpenses = new FilteredList<>(this.expenseList.getExpenseList());
        filteredExpenses = new FilteredList<>(this.expenseList.getExpenseList());
        filteredBudgets = new FilteredList<>(this.budgetList.getBudgetList());
    }

    public ModelManager() {
        this(new ExpenseList(), new BudgetList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public Path getExpenseListFilePath() {
        return userPrefs.getExpenseListFilePath();
    }

    @Override
    public void setExpenseListFilePath(Path expenseListFilePath) {
        requireNonNull(expenseListFilePath);
        userPrefs.setExpenseListFilePath(expenseListFilePath);
    }

    @Override
    public Path getBudgetListFilePath() {
        return userPrefs.getBudgetListFilePath();
    }

    @Override
    public void setBudgetListFilePath(Path budgetListFilePath) {
        requireNonNull(budgetListFilePath);
        userPrefs.setBudgetListFilePath(budgetListFilePath);
    }

    //=========== ExpenseList ================================================================================

    @Override
    public ReadOnlyExpenseList getExpenseList() {
        return expenseList;
    }

    @Override
    public void setExpenseList(ReadOnlyExpenseList expenseList) {
        this.expenseList.resetData(expenseList);
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        if (expenseFallsIntoABudget(expense)) {
            return getBudgetExpenseFallsInto(expense).get().budgetHasExpense(expense);
        } else {
            return expenseList.hasExpense(expense);
        }
    }

    @Override
    public void deleteExpense(Expense target) {
        expenseList.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        if (expenseFallsIntoABudget(expense)) {
            getBudgetExpenseFallsInto(expense).get().addExpenseIntoBudget(expense);
        } else {
            expenseList.addExpense(expense);
            updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        }
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        expenseList.setExpense(target, editedExpense);
    }

    //=========== Filtered Expense List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedExpenseList}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return filteredExpenses;
    }

//    @Override
//    public ObservableList<Expense> getFilteredFullExpenseList() {
//        ArrayList<Expense> list = new ArrayList<>();
//        for (Expense expense : this.expenseList.getExpenseList()) {
//            list.add(expense);
//        }
//        for (Budget budget : this.budgetList.getBudgetList()) {
//            for (Expense expense : budget.getExpenseList().getExpenseList()) {
//                list.add(expense);
//            }
//        }
//        Collections.sort(list);
//        return FXCollections.observableList(list);
//    }

    @Override
    public void updateFilteredExpenseList(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
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
        return expenseList.equals(other.expenseList)
            && userPrefs.equals(other.userPrefs)
            && filteredExpenses.equals(other.filteredExpenses)
            && budgetList.equals(other.budgetList)
            && filteredBudgets.equals(other.filteredBudgets);
    }

    //=========== BudgetList ================================================================================

    @Override
    public ReadOnlyBudgetList getBudgetList() {
        return budgetList;
    }

    @Override
    public void setBudgetList(ReadOnlyBudgetList budgetList) {
        this.budgetList.resetData(budgetList);
    }

    @Override
    public boolean hasBudget(Budget budget) {
        requireNonNull(budget);
        return budgetList.hasBudget(budget);
    }

    @Override
    public void deleteBudget(Budget target) {
        budgetList.removeBudget(target);
    }

    @Override
    public void addBudget(Budget budget) {
        budgetList.addBudget(budget);
        updateFilteredBudgetList(PREDICATE_SHOW_ALL_BUDGETS);
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        requireAllNonNull(target, editedBudget);

        budgetList.setBudget(target, editedBudget);
    }

    @Override
    public Optional<Budget> getBudgetExpenseFallsInto(Expense expense) {
        requireNonNull(expense);
        return budgetList.getBudgetExpenseFallsInto(expense);
    }

    @Override
    public boolean expenseFallsIntoABudget(Expense expense) {
        return getBudgetExpenseFallsInto(expense).isPresent();
    }

    @Override
    public ObservableList<Expense> getExpenseListFromBudget(Budget budgetToView) {
        return budgetToView.getObservableExpenseList();
    }

    @Override
    public boolean hasBudgetPeriodClash(Budget budget) {
        requireNonNull(budget);
        return budgetList.hasBudgetPeriodClash(budget);
    }

    //=========== Filtered Budget List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Budget} backed by the internal list of
     * {@code versionedExpenseList}
     */
    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return filteredBudgets;
    }

    @Override
    public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        requireNonNull(predicate);
        filteredBudgets.setPredicate(predicate);
    }
}
