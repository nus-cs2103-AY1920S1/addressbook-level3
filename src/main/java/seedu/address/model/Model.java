package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.budget.Budget;
import seedu.address.model.exchangedata.ExchangeData;

import seedu.address.model.expense.Expense;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;
    Predicate<Budget> PREDICATE_SHOW_ALL_BUDGETS = unused -> true;

    public ViewState getViewState();

    public void setViewState(ViewState viewState);

    public Budget getLastViewedBudget();

    public void setLastViewedBudget(Budget budget);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

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
     * Returns the ExpenseList
     */
    ReadOnlyExpenseList getExpenseList();

    /**
     * Replaces expense list data with the data in {@code expenseList}.
     */
    void setExpenseList(ReadOnlyExpenseList expenseList);

    /**
     * Returns the user prefs' exchangeData file path.
     */
    Path getExchangeDataFilePath();

    /**
     * Sets the user prefs' exchangeData file path.
     */
    void setExchangeDataFilePath(Path exchangeDataFilePath);

    /**
     * Replaces exchange data with the data in {@code exchangeData}.
     */
    void setExchangeData(ExchangeData exchangeData);

    /**
     * Returns the ExchangeData
     * */
    ExchangeData getExchangeData();

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

    ObservableList<Expense> getExpenses();

    void updateFilteredExpenses(Predicate<Expense> predicate);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the expense list.
     * The expense identity of {@code editedExpense} must not be the same as
     * another existing expense in the expense list.
     */
    void setExpense(Expense target, Expense editedExpense);

    /**
     * Returns an unmodifiable view of the filtered expense list
     */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     *
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
     * Returns the BudgetList
     */
    ReadOnlyBudgetList getBudgetList();

    /**
     * Replaces budget list data with the data in {@code budgetList}.
     */
    void setBudgetList(ReadOnlyBudgetList budgetList);

    /**
     * Returns true if a budget with the same identity as {@code budget} exists in the budget list.
     */
    boolean hasBudget(Budget budget);

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

    /**
     * Returns an unmodifiable view of the filtered budget list
     */
    ObservableList<Budget> getFilteredBudgetList();

    /**
     * Updates the filter of the filtered budget list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBudgetList(Predicate<Budget> predicate);

    boolean hasBudgetPeriodClash(Budget newBudget);

    Optional<Budget> getBudgetExpenseFallsInto(Expense expense);

    boolean expenseFallsIntoABudget(Expense expense);

    ObservableList<Expense> getExpenseListFromBudget(Budget budgetToView);

    ObservableList<Expense> initExpenses();
}
