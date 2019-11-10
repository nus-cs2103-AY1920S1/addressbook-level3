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
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.GotoCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.Storage;
import seedu.mark.storage.StorageStub;

class GotoReminderCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMark(), new UserPrefs());
    private Storage storage = new StorageStub();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reminder reminderToSearch = model.getReminders().get(INDEX_FIRST_REMINDER.getZeroBased());
        GotoReminderCommand gotoReminderCommand = new GotoReminderCommand(INDEX_FIRST_REMINDER);

        expectedModel.setCurrentUrl(reminderToSearch.getUrl());
        String expectedMessage =
                String.format(GotoReminderCommand.MESSAGE_GOTO_REMINDER_ACKNOWLEDGEMENT, reminderToSearch);
        CommandResult expectedCommandResult = new GotoCommandResult(expectedMessage);

        assertCommandSuccess(gotoReminderCommand, model, storage, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getReminders().size() + 1);
        GotoReminderCommand gotoReminderCommand = new GotoReminderCommand(outOfBoundIndex);

        assertCommandFailure(gotoReminderCommand, model, storage, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {

        Reminder reminderToOpen = model.getReminders().get(INDEX_FIRST_REMINDER.getZeroBased());
        GotoReminderCommand gotoReminderCommand = new GotoReminderCommand(INDEX_FIRST_REMINDER);

        expectedModel.setCurrentUrl(reminderToOpen.getUrl());
        String expectedMessage =
                String.format(GotoReminderCommand.MESSAGE_GOTO_REMINDER_ACKNOWLEDGEMENT, reminderToOpen);
        CommandResult expectedCommandResult = new GotoCommandResult(expectedMessage);

        assertCommandSuccess(gotoReminderCommand, model, storage, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        GotoReminderCommand gotoFirstCommand = new GotoReminderCommand(INDEX_FIRST_REMINDER);
        GotoReminderCommand gotoSecondCommand = new GotoReminderCommand(INDEX_SECOND_REMINDER);

        // same object -> returns true
        assertTrue(gotoFirstCommand.equals(gotoFirstCommand));

        // same values -> returns true
        GotoReminderCommand gotoFirstCommandCopy = new GotoReminderCommand(INDEX_FIRST_REMINDER);
        assertTrue(gotoFirstCommand.equals(gotoFirstCommandCopy));

        // different types -> returns false
        assertFalse(gotoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gotoFirstCommand.equals(null));

        // different bookmark -> returns false
        assertFalse(gotoFirstCommand.equals(gotoSecondCommand));
    }

}
