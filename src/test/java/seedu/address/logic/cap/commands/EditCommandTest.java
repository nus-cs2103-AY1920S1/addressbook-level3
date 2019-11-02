package seedu.address.logic.cap.commands;

import static seedu.address.testutil.TypicalTasks.getTypicalCalendarAddressBook;

import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;
import seedu.address.model.calendar.CalendarUserPrefs;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * EditCommand.
 */
public class EditCommandTest {

    private CalendarModel model = new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());

    //    @Test
    //    public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //        Task editedTask = new TaskBuilder().build();
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //        CalendarModel expectedModel =
    //            new CalendarModelManager(
    //                new CalendarAddressBook(model.getCalendarAddressBook()), new CalendarUserPrefs());
    //        expectedModel.setTask(model.getFilteredTaskList().get(0), editedPerson);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //        Index indexLastPerson = Index.fromOneBased(model.getFilteredTaskList().size());
    //        Task lastPerson = model.getFilteredTaskList().get(indexLastPerson.getZeroBased());
    //
    //        TaskBuilder TaskInList = new TaskBuilder(lastPerson);
    //        Task editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //                .withTags(VALID_TAG_HUSBAND).build();
    //
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
    //                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
    //        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //        CalendarModel expectedModel =
    //            new CalendarModelManager(new AddressBook(model.getAddressBook()), new CalendarUserPrefs());
    //        expectedModel.setTask(lastPerson, editedPerson);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_noFieldSpecifiedUnfilteredList_success() {
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
    //        Task editedPerson = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //        CalendarModel expectedModel =
    //            new CalendarModelManager(new AddressBook(model.getAddressBook()), new CalendarUserPrefs());
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_filteredList_success() {
    //        showTaskAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        Task personInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        Task editedPerson = new TaskBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //        CalendarModel expectedModel =
    //            new CalendarModelManager(new AddressBook(model.getAddressBook()), new CalendarUserPrefs());
    //        expectedModel.setTask(model.getFilteredTaskList().get(0), editedPerson);
    //
    //        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    //    }
    //
    //    @Test
    //    public void execute_duplicateTaskUnfilteredList_failure() {
    //        Task firstPerson = model.getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
    //        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);
    //
    //        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    //    }
    //
    //    @Test
    //    public void execute_duplicatePersonFilteredList_failure() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //        // edit Task in filtered list into a duplicate in address book
    //        Task personInList = model.getAddressBook().getTaskList().get(INDEX_SECOND_PERSON.getZeroBased());
    //        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //                new EditPersonDescriptorBuilder(personInList).build());
    //
    //        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    //    }
    //
    //    @Test
    //    public void execute_invalidPersonIndexUnfilteredList_failure() {
    //        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
    //        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
    //
    //        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }
    //
    //    /**
    //     * Edit filtered list where index is larger than size of filtered list,
    //     * but smaller than size of address book
    //     */
    //    @Test
    //    public void execute_invalidTaskIndexFilteredList_failure() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //        Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());
    //
    //        EditCommand editCommand = new EditCommand(outOfBoundIndex,
    //                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    //    }
    //
    //    @Test
    //    public void equals() {
    //        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);
    //
    //        // same values -> returns true
    //        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
    //        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
    //        assertTrue(standardCommand.equals(commandWithSameValues));
    //
    //        // same object -> returns true
    //        assertTrue(standardCommand.equals(standardCommand));
    //
    //        // null -> returns false
    //        assertFalse(standardCommand.equals(null));
    //
    //        // different types -> returns false
    //        assertFalse(standardCommand.equals(new ClearCommand()));
    //
    //        // different index -> returns false
    //        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));
    //
    //        // different descriptor -> returns false
    //        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    //    }
}
