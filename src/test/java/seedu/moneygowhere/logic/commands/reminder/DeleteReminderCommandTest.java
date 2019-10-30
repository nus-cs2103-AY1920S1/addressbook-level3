package seedu.moneygowhere.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.commons.core.index.Index;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.reminder.Reminder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteReminderCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() {
        Reminder reminderToDelete = model.getSortedReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        System.out.println(model);
        System.out.println(expectedModel);
        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedReminderList().size() + 1);
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(outOfBoundIndex);

        assertCommandFailure(deleteReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteReminderFirstCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);
        DeleteReminderCommand deleteReminderSecondCommand = new DeleteReminderCommand(INDEX_SECOND_REMINDER);

        // same object -> returns true
        assertTrue(deleteReminderFirstCommand.equals(deleteReminderFirstCommand));

        // same values -> returns true
        DeleteReminderCommand deleteReminderFirstCommandCopy = new DeleteReminderCommand(INDEX_FIRST_REMINDER);
        assertTrue(deleteReminderFirstCommand.equals(deleteReminderFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteReminderFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteReminderFirstCommand == null);

        // different Spending -> returns false
        assertFalse(deleteReminderFirstCommand.equals(deleteReminderSecondCommand));
    }
}
