package seedu.billboard.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.billboard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.billboard.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;
import seedu.billboard.model.expense.Expense;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalBillboard(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Expense expenseToDelete = model.getFilteredExpenses().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getBillboardExpenses(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenses().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);

        Expense expenseToDelete = model.getFilteredExpenses().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        Model expectedModel = new ModelManager(model.getBillboardExpenses(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        showNoExpense(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);

        Index outOfBoundIndex = INDEX_SECOND_EXPENSE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBillboardExpenses().getExpenses().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_EXPENSE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_EXPENSE);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_EXPENSE);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different expense -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

    /**
     * Updates {@code model}'s filtered list to show no expense.
     */
    private void showNoExpense(Model model) {
        model.updateFilteredExpenses(p -> false);

        assertTrue(model.getFilteredExpenses().isEmpty());
    }
}
