package seedu.mark.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_NOTE_OPEN;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.testutil.TypicalBookmarks.getTypicalMark;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.mark.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.mark.commons.core.Messages;
import seedu.mark.commons.core.index.Index;
import seedu.mark.model.Mark;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;
import seedu.mark.model.UserPrefs;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.StorageStub;
import seedu.mark.testutil.EditReminderDescriptorBuilder;
import seedu.mark.testutil.ReminderBuilder;

class EditReminderCommandTest {
    private Model model = new ModelManager(getTypicalMark(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Reminder editedReminder = new ReminderBuilder().build();
        EditReminderCommand.EditReminderDescriptor descriptor =
                new EditReminderDescriptorBuilder(editedReminder).build();
        EditReminderCommand editReminderCommand = new EditReminderCommand(INDEX_FIRST_REMINDER, descriptor);

        Reminder toBeEditReminder = model.getReminders().get(0);
        Reminder finalReminder = new ReminderBuilder().withUrl(toBeEditReminder.getUrl().toString()).build();
        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, finalReminder);

        Model expectedModel = new ModelManager(new Mark(model.getMark()), new UserPrefs());
        expectedModel.editReminder(model.getReminders().get(0), editedReminder);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(editReminderCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastReminder = Index.fromOneBased(model.getReminders().size());
        Reminder lastReminder = model.getReminders().get(indexLastReminder.getZeroBased());

        ReminderBuilder reminderInList = new ReminderBuilder(lastReminder);
        Reminder editedReminder = reminderInList.withNote(VALID_NOTE_OPEN)
                .build();

        EditReminderCommand.EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withNote(VALID_NOTE_OPEN)
                .build();
        EditReminderCommand editReminderCommand = new EditReminderCommand(indexLastReminder, descriptor);

        Reminder finalReminder = new ReminderBuilder(editedReminder).withUrl(lastReminder.getUrl().toString()).build();
        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, finalReminder);

        Model expectedModel = new ModelManager(new Mark(model.getMark()), new UserPrefs());
        expectedModel.editReminder(lastReminder, editedReminder);
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(editReminderCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        EditReminderCommand editReminderCommand =
                new EditReminderCommand(INDEX_FIRST_REMINDER, new EditReminderCommand.EditReminderDescriptor());
        Reminder editedReminder = model.getReminders().get(INDEX_FIRST_REMINDER.getZeroBased());

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new Mark(model.getMark()), new UserPrefs());
        expectedModel.saveMark(expectedMessage);

        assertCommandSuccess(editReminderCommand, model, new StorageStub(), expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidReminderIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getReminders().size() + 1);
        EditReminderCommand.EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withNote(VALID_NOTE_OPEN).build();
        EditReminderCommand editReminderCommand = new EditReminderCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editReminderCommand,
                model, new StorageStub(), Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditReminderCommand.EditReminderDescriptor descOpen =
                new EditReminderDescriptorBuilder()
                        .withNote("OPEN")
                        .build();
        EditReminderCommand.EditReminderDescriptor descRead =
                new EditReminderDescriptorBuilder().withNote("READ").build();

        final EditReminderCommand standardCommand =
                new EditReminderCommand(INDEX_FIRST_REMINDER, descOpen);

        // same values -> returns true
        //TODO: Check what's go wrong here
        EditReminderCommand.EditReminderDescriptor copyDescriptor =
                new EditReminderCommand.EditReminderDescriptor(descOpen);
        EditReminderCommand commandWithSameValues =
                new EditReminderCommand(INDEX_FIRST_REMINDER, copyDescriptor);
        assertTrue(descOpen.equals(copyDescriptor));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_SECOND_REMINDER, descOpen)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_FIRST_REMINDER, descRead)));
    }
}
