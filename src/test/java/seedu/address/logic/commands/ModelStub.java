package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.ReadOnlyBudgetList;
import seedu.address.model.expense.Expense;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getExpenseListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExpenseListFilePath(Path expenseListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addExpense(Expense expense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Expense> getExpenses() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredExpenses(Predicate<Expense> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyExpenseList getExpenseList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExpenseList(ReadOnlyExpenseList newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExpense(Expense expense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteExpense(Expense target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Expense> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getBudgetListFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBudgetListFilePath(Path budgetListFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addBudget(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyBudgetList getBudgetList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBudgetList(ReadOnlyBudgetList newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBudget(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteBudget(Budget target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setBudget(Budget target, Budget editedBudget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredBudgetList(Predicate<Budget> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Budget> getBudgetExpenseFallsInto(Expense expense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBudgetPeriodClash(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean expenseFallsIntoABudget(Expense expense) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Expense> getExpenseListFromBudget(Budget budgetToView) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Expense> initExpenses() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setViewState(ViewState viewState) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ViewState getViewState() {
        throw new AssertionError("This method should not be called.");
    };

    @Override
    public Budget getLastViewedBudget() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLastViewedBudget(Budget budget) {
        throw new AssertionError("This method should not be called.");
    }
}