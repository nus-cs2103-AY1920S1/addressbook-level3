package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.ReadOnlyBudgetList;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.UniqueExpenseList;
import seedu.address.testutil.ExpenseBuilder;

public class AddExpenseCommandTest {

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExpenseCommand(null));
    }

    @Test
    public void execute_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Expense validExpense = new ExpenseBuilder().build();

        CommandResult commandResult = new AddExpenseCommand(validExpense).execute(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExpense), modelStub.expensesAdded);
    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense validExpense = new ExpenseBuilder().build();
        AddExpenseCommand addExpenseCommand = new AddExpenseCommand(validExpense);
        ModelStub modelStub = new ModelStubWithExpense(validExpense);

        assertThrows(CommandException.class, AddExpenseCommand.MESSAGE_DUPLICATE_EXPENSE, () -> addExpenseCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Expense shopping = new ExpenseBuilder().withName("Adidas shoe").build();
        Expense food = new ExpenseBuilder().withName("Lunch").build();
        AddExpenseCommand addShoppingCommand = new AddExpenseCommand(shopping);
        AddExpenseCommand addFoodCommand = new AddExpenseCommand(food);

        // same object -> returns true
        assertTrue(addShoppingCommand.equals(addShoppingCommand));

        // same values -> returns true
        AddExpenseCommand addShoppingCommandCopy = new AddExpenseCommand(shopping);
        assertTrue(addShoppingCommand.equals(addShoppingCommandCopy));

        // different types -> returns false
        assertFalse(addShoppingCommand.equals(1));

        // null -> returns false
        assertFalse(addShoppingCommand.equals(null));

        // different expense -> returns false
        assertFalse(addShoppingCommand.equals(addFoodCommand));
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
        public ViewState getViewState() {
            return ViewState.DEFAULT_EXPENSELIST;
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
        public ViewState getViewState() {
            return ViewState.DEFAULT_EXPENSELIST;
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            return new FilteredList<Expense>(new UniqueExpenseList().asUnmodifiableObservableList());
        }

        @Override
        public ReadOnlyExpenseList getExpenseList() {
            return new ExpenseList();
        }
    }
}
