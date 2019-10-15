//package seedu.address.logic.calendar.commands;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.DeleteCommand;
//import seedu.address.calendarModel.task.Task;
//
//
///**
// * Contains integration tests (interaction with the CalendarModel, UndoCommand and RedoCommand) and unit tests for
// * {@code DeleteCommand}.
// */
//public class DeleteCommandTest {
//
//    private CalendarModel calendarModel = new CalendarModelManager(getTypicalAddressBook(), new CalendarUserPrefs());
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        Task personToDelete = calendarModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
//
//        CalendarModelManager expectedModel
//                = new CalendarModelManager(calendarModel.getCalendarAddressBook(), new CalendarUserPrefs());
//        expectedModel.deletePerson(personToDelete);
//
//        assertCommandSuccess(deleteCommand, calendarModel, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
//        Index outOfBoundIndex = Index.fromOneBased(calendarModel.getFilteredPersonList().size() + 1);
//        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, calendarModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showPersonAtIndex(calendarModel, INDEX_FIRST_PERSON);
//
//        Task personToDelete = calendarModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
//
//        CalendarModel expectedModel
//                = new CalendarModelManager(calendarModel.getCalendarAddressBook(), new CalendarUserPrefs());
//        expectedModel.deletePerson(personToDelete);
//        showNoPerson(expectedModel);
//
//        assertCommandSuccess(deleteCommand, calendarModel, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() {
//        showPersonAtIndex(calendarModel, INDEX_FIRST_PERSON);
//
//        Index outOfBoundIndex = INDEX_SECOND_PERSON;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < calendarModel.getCalendarAddressBook().getPersonList().size());
//
//        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
//
//        assertCommandFailure(deleteCommand, calendarModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void equals() {
//        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
//        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
//
//        // same object -> returns true
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
//
//        // same values -> returns true
//        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
//        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(deleteFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(deleteFirstCommand.equals(null));
//
//        // different task -> returns false
//        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
//    }
//
//    /**
//     * Updates {@code calendarModel}'s filtered list to show no one.
//     */
//    private void showNoPerson(CalendarModel calendarModel) {
//        calendarModel.updateFilteredPersonList(p -> false);
//
//        assertTrue(calendarModel.getFilteredPersonList().isEmpty());
//    }
//}
