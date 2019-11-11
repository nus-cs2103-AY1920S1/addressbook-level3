package seedu.address.logic.calendar.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTasks.getTypicalCalendarAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;
import seedu.address.model.calendar.CalendarUserPrefs;
import seedu.address.model.calendar.task.Task;


/**
 * Contains integration tests (interaction with the CalendarModel, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private CalendarModel calendarModel =
        new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task personToDelete = calendarModel.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        CalendarModelManager expectedModel =
            new CalendarModelManager(calendarModel.getCalendarAddressBook(), new CalendarUserPrefs());
        expectedModel.deleteTask(personToDelete);

        assertCommandSuccess(deleteCommand, calendarModel, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code calendarModel}'s filtered list to show no one.
     */
    private void showNoTask(CalendarModel calendarModel) {
        calendarModel.updateFilteredTaskList(p -> false);

        assertTrue(calendarModel.getFilteredTaskList().isEmpty());
    }
}
