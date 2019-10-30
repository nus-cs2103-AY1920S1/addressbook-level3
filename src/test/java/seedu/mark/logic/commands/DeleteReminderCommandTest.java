package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.StorageStub;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) for
 * {@code DeleteReminderCommand}.
 */
class DeleteReminderCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() {
        Reminder reminderToDelete = model.getReminders().get(INDEX_FIRST_REMINDER.getZeroBased());
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(model.getMark(), new UserPrefs());
        expectedModel.removeReminder(reminderToDelete);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(deleteReminderCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookmarkList().size() + 1);
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(outOfBoundIndex);

        assertCommandFailure(deleteReminderCommand, model, new StorageStub(),
                Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteFirstReminderCommand = new DeleteReminderCommand(INDEX_FIRST_REMINDER);
        DeleteReminderCommand deleteSecondReminderCommand = new DeleteReminderCommand(INDEX_SECOND_REMINDER);

        // same object -> returns true
        assertTrue(deleteFirstReminderCommand.equals(deleteFirstReminderCommand));

        // same values -> returns true
        DeleteReminderCommand deleteFirstReminderCommandCopy = new DeleteReminderCommand(INDEX_FIRST_REMINDER);
        assertTrue(deleteFirstReminderCommand.equals(deleteFirstReminderCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstReminderCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstReminderCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(deleteFirstReminderCommand.equals(deleteSecondReminderCommand));
    }

}
