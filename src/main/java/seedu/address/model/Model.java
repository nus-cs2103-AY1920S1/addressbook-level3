package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.ReadOnlyBudgetList;
import seedu.address.model.expense.Expense;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;
    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

    void setViewState(String state);

    String getViewState();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' expense list file path.
     */
    Path getExpenseListFilePath();

    /**
     * Sets the user prefs' expense list file path.
     */
    void setExpenseListFilePath(Path expenseListFilePath);

    /**
     * Replaces expense list data with the data in {@code expenseList}.
     */
    void setExpenseList(ReadOnlyExpenseList expenseList);

    /** Returns the ExpenseList */
    ReadOnlyExpenseList getExpenseList();

    /**
     * Returns true if an expense with the same identity as {@code expense} exists in the expense list.
     */
    boolean hasExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the expense list.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     * {@code expense} must not already exist in the expense list.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the expense list.
     * The expense identity of {@code editedExpense} must not be the same as
     * another existing expense in the expense list.
     */
    void setExpense(Expense target, Expense editedExpense);

    /** Returns an unmodifiable view of the filtered expense list */
    ObservableList<Expense> getFilteredExpenseList();

    /** Returns an unmodifiable view of the filtered full expense list that includes those in budgets */
    ObservableList<Expense> getFilteredFullExpenseList();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Expense> predicate);

    /**
     * Returns the user prefs' budget list file path.
     */
    Path getBudgetListFilePath();

    /**
     * Sets the user prefs' budget list file path.
     */
    void setBudgetListFilePath(Path expenseListFilePath);

    /**
     * Replaces budget list data with the data in {@code budgetList}.
     */
    void setBudgetList(ReadOnlyBudgetList budgetList);

    /** Returns the BudgetList */
    ReadOnlyBudgetList getBudgetList();

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the budget list.
     */
    boolean hasBudget(Budget budget);

    void viewBudget(Budget target);

    /**
     * Deletes the given budget.
     * The budget must exist in the budget list.
     */
    void deleteBudget(Budget target);

    /**
     * Adds the given budget.
     * {@code budget} must not already exist in the budget list.
     */
    void addBudget(Budget budget);

    /**
     * Replaces the given budget {@code target} with {@code editedBudget}.
     * {@code target} must exist in the budget list.
     * The budget identity of {@code editedBudget} must not be the same as
     * another existing budget in the budget list.
     */
    void setBudget(Budget target, Budget editedBudget);

    /** Returns an unmodifiable view of the filtered budget list */
    ObservableList<Budget> getFilteredBudgetList();

    /**
     * Updates the filter of the filtered budget list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBudgetList(Predicate<Budget> predicate);

    boolean hasBudgetPeriodClash(Budget newBudget);

    Optional<Budget> getBudgetExpenseFallsInto(Expense expense);

    boolean expenseFallsIntoABudget(Expense expense);
}
