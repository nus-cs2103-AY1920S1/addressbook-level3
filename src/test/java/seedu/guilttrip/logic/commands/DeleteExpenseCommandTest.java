package seedu.guilttrip.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.guilttrip.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.CommandHistoryStub;
import seedu.guilttrip.logic.commands.deletecommands.DeleteExpenseCommand;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteExpenseCommandTest {

    private Model model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
    private CommandHistory chs = new CommandHistoryStub();

    /*@Test
    public void execute_validIndexUnfilteredList_success() {
        Expense expenseToDelete = model.getFilteredExpenses().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteExpenseCommand deleteCommand = new DeleteExpenseCommand(INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_ENTRY_SUCCESS, expenseToDelete);

        ModelManager expectedModel = new ModelManager(model.getGuiltTrip(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, chs);
    }*/

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenses().size() + 1);
        DeleteExpenseCommand deleteCommand = new DeleteExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX, chs);
    }

    /*@Test
    public void execute_validIndexFilteredList_success() {
        //work on a filtered list
        showExpenseAtIndex(model, INDEX_FIRST_ENTRY);

        Expense expenseToDelete = model.getFilteredExpenses().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteExpenseCommand deleteCommand = new DeleteExpenseCommand(INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_ENTRY_SUCCESS, expenseToDelete);

        Model expectedModel = new ModelManager(model.getGuiltTrip(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitGuiltTrip();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel, chs);
    }*/

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showExpenseAtIndex(model, INDEX_FIRST_ENTRY);

        Index outOfBoundIndex = INDEX_SECOND_ENTRY;
        // ensures that outOfBoundIndex is still in bounds of guilttrip book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getGuiltTrip().getExpenseList().size());

        DeleteExpenseCommand deleteCommand = new DeleteExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX, chs);
    }

    @Test
    public void equals() {
        DeleteExpenseCommand deleteFirstCommand = new DeleteExpenseCommand(INDEX_FIRST_ENTRY);
        DeleteExpenseCommand deleteSecondCommand = new DeleteExpenseCommand(INDEX_SECOND_ENTRY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExpenseCommand deleteFirstCommandCopy = new DeleteExpenseCommand(INDEX_FIRST_ENTRY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different entry -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
