package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.ReadOnlyBudgetList;
import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

public class AddCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    //    @Test
    //    public void execute_expenseAcceptedByModel_addSuccessful() throws Exception {
    //        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
    //        Expense validExpense = new ExpenseBuilder().build();
    //
    //        CommandResult commandResult = new AddCommand(validExpense).execute(modelStub);
    //
    //        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validExpense), commandResult.getFeedbackToUser());
    //        assertEquals(Arrays.asList(validExpense), modelStub.expensesAdded);
    //    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense validExpense = new ExpenseBuilder().build();
        AddCommand addCommand = new AddCommand(validExpense);
        ModelStub modelStub = new ModelStubWithExpense(validExpense);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_EXPENSE, () -> addCommand.execute(modelStub,
            commandHistory));
    }

    @Test
    public void equals() {
        Expense shopping = new ExpenseBuilder().withName("Adidas shoe").build();
        Expense food = new ExpenseBuilder().withName("Lunch").build();
        AddCommand addShoppingCommand = new AddCommand(shopping);
        AddCommand addFoodCommand = new AddCommand(food);

        // same object -> returns true
        assertTrue(addShoppingCommand.equals(addShoppingCommand));

        // same values -> returns true
        AddCommand addShoppingCommandCopy = new AddCommand(shopping);
        assertTrue(addShoppingCommand.equals(addShoppingCommandCopy));

        // different types -> returns false
        assertFalse(addShoppingCommand.equals(1));

        // null -> returns false
        assertFalse(addShoppingCommand.equals(null));

        // different expense -> returns false
        assertFalse(addShoppingCommand.equals(addFoodCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

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
        public ReadOnlyExpenseList getExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpenseList(ReadOnlyExpenseList newData) {
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

        public void updateFilteredExpenses(Predicate<Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getExchangeDataFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExchangeDataFilePath(Path exchangeDataFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ExchangeData getExchangeData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExchangeData(ExchangeData exchangeData) {
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

        public void viewBudget(Budget target) {
            throw new AssertionError("This method should not be called.");
        }

        public ViewState getViewState() {
            throw new AssertionError("This method should not be called.");
        }

        public void setViewState(ViewState viewState) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Budget getLastViewedBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLastViewedBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single expense.
     */
    private class ModelStubWithExpense extends ModelStub {

        private final Expense expense;

        ModelStubWithExpense(Expense expense) {
            requireNonNull(expense);
            this.expense = expense;
        }

        @Override
        public boolean hasExpense(Expense expense) {
            requireNonNull(expense);
            return this.expense.isSameExpense(expense);
        }
    }

    /**
     * A Model stub that always accept the expense being added.
     */
    private class ModelStubAcceptingExpenseAdded extends ModelStub {

        final ArrayList<Expense> expensesAdded = new ArrayList<>();

        @Override
        public boolean hasExpense(Expense expense) {
            requireNonNull(expense);
            return expensesAdded.stream().anyMatch(expense::isSameExpense);
        }

        @Override
        public void addExpense(Expense expense) {
            requireNonNull(expense);
            expensesAdded.add(expense);
        }

        @Override
        public ReadOnlyExpenseList getExpenseList() {
            return new ExpenseList();
        }
    }
}
