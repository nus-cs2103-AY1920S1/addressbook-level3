package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditNoteCommand.EditNoteDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditNoteCommand.
 */
public class EditNoteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Note editedNote = new PersonBuilder().build();
        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(editedNote).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setNote(model.getFilteredNoteList().get(0), editedNote);

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredNoteList().size());
        Note lastNote = model.getFilteredNoteList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastNote);
        Note editedNote = personInList.withTitle(VALID_TITLE_BOB).build();

        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder()
                .withTitle(VALID_TITLE_BOB).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setNote(lastNote, editedNote);

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST_PERSON,
                new EditNoteCommand.EditNoteDescriptor());
        Note editedNote = model.getFilteredNoteList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Note noteInFilteredList = model.getFilteredNoteList().get(INDEX_FIRST_PERSON.getZeroBased());
        Note editedNote = new PersonBuilder(noteInFilteredList).withTitle(VALID_TITLE_BOB).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST_PERSON,
                new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).build());

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setNote(model.getFilteredNoteList().get(0), editedNote);

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Note firstNote = model.getFilteredNoteList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(firstNote).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editNoteCommand, model, EditNoteCommand.MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Note noteInList = model.getAddressBook().getNoteList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST_PERSON,
                new EditNoteDescriptorBuilder(noteInList).build());

        assertCommandFailure(editNoteCommand, model, EditNoteCommand.MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredNoteList().size() + 1);
        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getNoteList().size());

        EditNoteCommand editNoteCommand = new EditNoteCommand(outOfBoundIndex,
                new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).build());

        assertCommandFailure(editNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditNoteCommand standardCommand = new EditNoteCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditNoteDescriptor copyDescriptor = new EditNoteCommand.EditNoteDescriptor(DESC_AMY);
        EditNoteCommand commandWithSameValues = new EditNoteCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearNoteCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditNoteCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditNoteCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }
}
