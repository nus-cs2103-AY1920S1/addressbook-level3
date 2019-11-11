package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.CommandHistoryStub;
import seedu.guilttrip.logic.commands.addcommands.AddExpenseCommand;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.ModelStub;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.util.CategoryType;
import seedu.guilttrip.testutil.ExpenseBuilder;

public class AddExpenseCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExpenseCommand(null));
    }

    @Test
    public void execute_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        CommandHistoryStub chs = new CommandHistoryStub();
        Expense validExpense = new ExpenseBuilder().build();

        CommandResult commandResult = new AddExpenseCommand(validExpense).execute(modelStub, chs);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExpense), modelStub.listOfExpenses);
    }

    @Test
    public void equals() {
        Expense laksaExpense = new ExpenseBuilder().withDesc("Laksa").build();
        Expense malaExpense = new ExpenseBuilder().withDesc("MalaWithDeath").build();
        AddExpenseCommand addLaksaCommand = new AddExpenseCommand(laksaExpense);
        AddExpenseCommand addMalaCommand = new AddExpenseCommand(malaExpense);

        // same object -> returns true
        assertTrue(addLaksaCommand.equals(addLaksaCommand));

        // same values -> returns true
        AddExpenseCommand addLaksaCommandCopy = new AddExpenseCommand(laksaExpense);
        assertTrue(addLaksaCommand.equals(addLaksaCommandCopy));

        // different types -> returns false
        assertFalse(addLaksaCommand.equals(1));

        // null -> returns false
        assertFalse(addLaksaCommand.equals(null));

        // different entry -> returns false
        assertFalse(addLaksaCommand.equals(addMalaCommand));
    }

    /**
     * A Model stub that contains a single entry.
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
            return this.expense.isSameEntry(expense);
        }
    }

    /**
     * A Model stub that always accept the expense being added.
     */
    private class ModelStubAcceptingExpenseAdded extends ModelStub {
        final ArrayList<Expense> listOfExpenses = new ArrayList<>();
        final ArrayList<Category> listOfCategories =
                new ArrayList<Category>(Arrays.asList(new Category("Food", CategoryType.EXPENSE)));
        @Override
        public boolean hasExpense(Expense expense) {
            requireNonNull(expense);
            return listOfExpenses.stream().anyMatch(expense::isSameEntry);
        }

        @Override
        public boolean hasCategory(Category category) {
            requireNonNull(category);
            return listOfCategories.stream().anyMatch(category::isSameCategory);
        }

        @Override
        public void addExpense(Expense expense) {
            requireNonNull(expense);
            listOfExpenses.add(expense);
        }

        //TODO
        @Override
        public void commitGuiltTrip() {
        }

        @Override
        public ReadOnlyGuiltTrip getGuiltTrip() {
            return new GuiltTrip(true);
        }
    }

}
