//package seedu.guilttrip.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.DESC_FOOD_EXPENSE;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.DESC_CLOTHING_EXPENSE;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_DESC_CLOTHING_EXPENSE;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_TAG_FOOD;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.guilttrip.logic.commands.CommandTestUtil.showPersonAtIndex;
//import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
//import static seedu.guilttrip.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
//import static seedu.guilttrip.testutil.TypicalEntries.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.commons.core.Messages;
//import seedu.guilttrip.commons.core.index.Index;
//import seedu.guilttrip.logic.commands.editcommands.EditCommand.EditPersonDescriptor;
//import seedu.guilttrip.model.GuiltTrip;
//import seedu.guilttrip.model.Model;
//import seedu.guilttrip.model.ModelManager;
//import seedu.guilttrip.model.UserPrefs;
//import seedu.guilttrip.model.entry.Person;
//import seedu.guilttrip.testutil.EditEntryDescriptorBuilder;
//import seedu.guilttrip.testutil.EntryBuilder;
//
///**
// * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
// and unit tests for EditCommand.
// */
//public class EditCommandTest {
//
//    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//
//    @Test
//    public void execute_allFieldsSpecifiedUnfilteredList_success() {
//        Person editedPerson = new EntryBuilder().build();
//        EditPersonDescriptor descriptor = new EditEntryDescriptorBuilder(editedPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new GuiltTrip(model.getAddressBook()), new UserPrefs());
//        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_someFieldsSpecifiedUnfilteredList_success() {
//        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
//        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
//
//        EntryBuilder personInList = new EntryBuilder(lastPerson);
//        Person editedPerson = personInList.withDesc(VALID_DESC_CLOTHING_EXPENSE).withPhone(VALID_PHONE_BOB)
//                .withTags(VALID_TAG_FOOD).build();
//
//        EditPersonDescriptor descriptor = new EditEntryDescriptorBuilder()
//                  .withDescription(VALID_DESC_CLOTHING_EXPENSE)
//                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_FOOD).build();
//        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new GuiltTrip(model.getAddressBook()), new UserPrefs());
//        expectedModel.setPerson(lastPerson, editedPerson);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_noFieldSpecifiedUnfilteredList_success() {
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
//        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new GuiltTrip(model.getAddressBook()), new UserPrefs());
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_filteredList_success() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        Person editedPerson = new EntryBuilder(personInFilteredList).withDesc(VALID_DESC_CLOTHING_EXPENSE).build();
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
//                new EditEntryDescriptorBuilder().withDescription(VALID_DESC_CLOTHING_EXPENSE).build());
//
//        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedPerson);
//
//        Model expectedModel = new ModelManager(new GuiltTrip(model.getAddressBook()), new UserPrefs());
//        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_duplicatePersonUnfilteredList_failure() {
//        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        EditPersonDescriptor descriptor = new EditEntryDescriptorBuilder(firstPerson).build();
//        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);
//
//        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENTRY);
//    }
//
//    @Test
//    public void execute_duplicatePersonFilteredList_failure() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        // edit entry in filtered list into a duplicate in guilttrip book
//        Person personInList = model.getAddressBook().getEntryList().get(INDEX_SECOND_PERSON.getZeroBased());
//        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
//                new EditEntryDescriptorBuilder(personInList).build());
//
//        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENTRY);
//    }
//
//    @Test
//    public void execute_invalidPersonIndexUnfilteredList_failure() {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//        EditPersonDescriptor descriptor = new EditEntryDescriptorBuilder()
//        .withDescription(VALID_DESC_CLOTHING_EXPENSE).build();
//        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
//
//        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    /**
//     * Edit filtered list where index is larger than size of filtered list,
//     * but smaller than size of guilttrip book
//     */
//    @Test
//    public void execute_invalidPersonIndexFilteredList_failure() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//        Index outOfBoundIndex = INDEX_SECOND_PERSON;
//        // ensures that outOfBoundIndex is still in bounds of guilttrip book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEntryList().size());
//
//        EditCommand editCommand = new EditCommand(outOfBoundIndex,
//                new EditEntryDescriptorBuilder().withDescription(VALID_DESC_CLOTHING_EXPENSE).build());
//
//        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void equals() {
//        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_FOOD_EXPENSE);
//
//        // same values -> returns true
//        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_FOOD_EXPENSE);
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
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_FOOD_EXPENSE)));
//
//        // different descriptor -> returns false
//        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_CLOTHING_EXPENSE)));
//    }
//
//}
