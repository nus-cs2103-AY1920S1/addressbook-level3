package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.Budget;
import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.expense.Expense;

/**
 * Represents the in-memory model of the expense list data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static ViewState viewState = ViewState.DEFAULT_EXPENSELIST;
    private static Budget lastViewedBudget;
    private final ExpenseList defaultExpenseList;
    private final BudgetList budgetList;
    private final UserPrefs userPrefs;
    private final FilteredList<Budget> filteredBudgets;
    private final FilteredList<Expense> filteredExpenses;
    private final FilteredList<Expense> expenses;
    private ExchangeData exchangeData;

    /**
     * Initializes a ModelManager with the given expenseList, budgetlist and userPrefs.
     */
    public ModelManager(ReadOnlyExpenseList expenseList, ReadOnlyBudgetList budgetList,
                        ExchangeData exchangeData, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseList, exchangeData, userPrefs);

        logger.fine("Initializing with expense list: " + expenseList + ", user prefs " + userPrefs
            + " budget list: " + budgetList);

        this.defaultExpenseList = new ExpenseList(expenseList);
        this.exchangeData = new ExchangeData(exchangeData);
        this.budgetList = new BudgetList(budgetList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpenses = new FilteredList<>(this.defaultExpenseList.getExpenseList());
        filteredBudgets = new FilteredList<>(this.budgetList.getBudgetList());
        expenses = new FilteredList<>(initExpenses());
    }

    public ModelManager() {
        this(new ExpenseList(), new BudgetList(), new ExchangeData(), new UserPrefs());
    }

    public ViewState getViewState() {
        return viewState;
    }

    public void setViewState(ViewState state) {
        viewState = state;
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    public Budget getLastViewedBudget() {
        return lastViewedBudget;
    }

    //=========== UserPrefs ==================================================================================

    public void setLastViewedBudget(Budget budget) {
        lastViewedBudget = budget;
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
    public Path getExchangeDataFilePath() {
        return userPrefs.getExchangeDataFilePath();
    }

    @Override
    public void setExchangeDataFilePath(Path exchangeDataFilePath) {
        requireNonNull(exchangeDataFilePath);
        userPrefs.setExchangeDataFilePath(exchangeDataFilePath);
    }

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
        return defaultExpenseList;
    }

    @Override
    public void setExpenseList(ReadOnlyExpenseList expenseList) {
        this.defaultExpenseList.resetData(expenseList);
    }

    @Override
    public ExchangeData getExchangeData() {
        return exchangeData;
    }

    @Override
    public void setExchangeData(ExchangeData exchangeData) {
        this.exchangeData = exchangeData;
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        Optional<Budget> budget = getBudgetExpenseFallsInto(expense);
        return budget.map(value -> value.budgetHasExpense(expense))
            .orElseGet(() -> defaultExpenseList.hasExpense(expense));
    }

    @Override
    public void deleteExpense(Expense target) {
        defaultExpenseList.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        Optional<Budget> budget = getBudgetExpenseFallsInto(expense);
        if (budget.isPresent()) {
            budget.get().addExpenseIntoBudget(expense);
        } else {
            defaultExpenseList.addExpense(expense);
            updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
            //            updateFilteredExpenses(PREDICATE_SHOW_ALL_EXPENSES);
        }
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        defaultExpenseList.setExpense(target, editedExpense);
    }

    @Override
    public ObservableList<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public void updateFilteredExpenses(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        expenses.setPredicate(predicate);
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
        return defaultExpenseList.equals(other.defaultExpenseList)
            && exchangeData.equals(other.exchangeData)
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

    /**
     * @return all the expenses
     */
    @Override
    public ObservableList<Expense> initExpenses() {
        ObservableList<Expense> allExpenses = FXCollections.observableArrayList(new ArrayList<>());
        allExpenses.addAll(this.defaultExpenseList.getExpenseList());
        for (Budget budget : this.budgetList.getBudgetList()) {
            allExpenses.addAll(budget.getExpenseList().getExpenseList());
        }
        return allExpenses;
    }
}
