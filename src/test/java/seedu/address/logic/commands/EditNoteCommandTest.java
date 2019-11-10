package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.NotesCommandTestUtil.DESC_DIARYONE;
import static seedu.address.logic.commands.NotesCommandTestUtil.DESC_DIARYTWO;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_DESCRIPTION_DIARYONE;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_STRING_COMMAND_ARG;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_TAG_WORK;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_TITLE_DIARYONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditNoteCommand.EditNoteDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.CardBook;
import seedu.address.model.FileBook;
import seedu.address.model.NoteBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PasswordBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.Note;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditNoteCommand.
 */
public class EditNoteCommandTest {

    private Model model = new ModelManager(new AddressBook(), new FileBook(), new CardBook(),
    getTypicalNoteBook(), new PasswordBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Note editedNote = new NoteBuilder().build();
        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(editedNote).build();
        EditNoteCommand editCommand = new EditNoteCommand(INDEX_FIRST, descriptor, VALID_STRING_COMMAND_ARG);

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        ModelManager expectedModel = new ModelManager(new AddressBook(), new FileBook(), new CardBook(),
                model.getNoteBook(), new PasswordBook(), new UserPrefs());
        expectedModel.setNote(model.getFilteredNoteList().get(0), editedNote);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastNote = Index.fromOneBased(model.getFilteredNoteList().size());
        Note lastNote = model.getFilteredNoteList().get(indexLastNote.getZeroBased());

        NoteBuilder noteInList = new NoteBuilder(lastNote);
        Note editedNote = noteInList.withTitle(VALID_TITLE_DIARYONE).withDescription(VALID_DESCRIPTION_DIARYONE)
                .withTags(VALID_TAG_WORK).build();

        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_DIARYONE)
                .withDescription(VALID_DESCRIPTION_DIARYONE).withTags(VALID_TAG_WORK).build();
        EditNoteCommand editCommand = new EditNoteCommand(indexLastNote, descriptor, VALID_STRING_COMMAND_ARG);

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AddressBook(), new FileBook(), new CardBook(),
                new NoteBook(model.getNoteBook()), new PasswordBook(), new UserPrefs());
        expectedModel.setNote(lastNote, editedNote);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditNoteCommand editCommand = new EditNoteCommand(INDEX_FIRST, new EditNoteDescriptor(),
                VALID_STRING_COMMAND_ARG);
        Note editedNote = model.getFilteredNoteList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AddressBook(), new FileBook(), new CardBook(),
                new NoteBook(model.getNoteBook()), new PasswordBook(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

//    @Test
//    public void execute_filteredList_success() {
//        showNoteAtIndex(model, INDEX_FIRST);
//
//        Note noteInFilteredList = model.getFilteredNoteList().get(INDEX_FIRST.getZeroBased());
//        Note editedNote = new NoteBuilder(noteInFilteredList).withTitle(VALID_NAME_BOB).build();
//        EditNoteCommand editCommand = new EditNoteCommand(INDEX_FIRST,
//                new EditNoteDescriptorBuilder().withTitle(VALID_NAME_BOB).build());
//
//        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);
//
//        Model expectedModel = new ModelManager(new NoteBook(model.getNoteBook()), new UserPrefs());
//        expectedModel.setNote(model.getFilteredNoteList().get(0), editedNote);
//
//        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_duplicateNoteUnfilteredList_failure() {
//        Note firstNote = model.getFilteredNoteList().get(INDEX_FIRST.getZeroBased());
//        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(firstNote).build();
//        EditNoteCommand editCommand = new EditNoteCommand(INDEX_SECOND, descriptor);
//
//        assertCommandFailure(editCommand, model, EditNoteCommand.MESSAGE_DUPLICATE_NOTE);
//    }
//
//    @Test
//    public void execute_duplicateNoteFilteredList_failure() {
//        showNoteAtIndex(model, INDEX_FIRST);
//
//        // edit note in filtered list into a duplicate in address book
//        Note noteInList = model.getNoteBook().getNoteList().get(INDEX_SECOND.getZeroBased());
//        EditNoteCommand editCommand = new EditNoteCommand(INDEX_FIRST,
//                new EditNoteDescriptorBuilder(noteInList).build());
//
//        assertCommandFailure(editCommand, model, EditNoteCommand.MESSAGE_DUPLICATE_NOTE);
//    }

    @Test
    public void execute_invalidNoteIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredNoteList().size() + 1);
        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder().withTitle(VALID_NAME_BOB).build();
        EditNoteCommand editCommand = new EditNoteCommand(outOfBoundIndex, descriptor, VALID_STRING_COMMAND_ARG);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }
//
//    /**
//     * Edit filtered list where index is larger than size of filtered list,
//     * but smaller than size of address book
//     */
//    @Test
//    public void execute_invalidNoteIndexFilteredList_failure() {
//        showNoteAtIndex(model, INDEX_FIRST);
//        Index outOfBoundIndex = INDEX_SECOND;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getNoteBook().getNoteList().size());
//
//        EditNoteCommand editCommand = new EditNoteCommand(outOfBoundIndex,
//                new EditNoteDescriptorBuilder().withTitle(VALID_NAME_BOB).build());
//
//        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
//    }

    @Test
    public void equals() {
        final EditNoteCommand standardCommand = new EditNoteCommand(INDEX_FIRST, DESC_DIARYONE,
                VALID_STRING_COMMAND_ARG);

        // same values -> returns true
        EditNoteDescriptor copyDescriptor = new EditNoteDescriptor(DESC_DIARYONE);
        EditNoteCommand commandWithSameValues = new EditNoteCommand(INDEX_FIRST, copyDescriptor,
                VALID_STRING_COMMAND_ARG);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditNoteCommand(INDEX_SECOND, DESC_DIARYONE, VALID_STRING_COMMAND_ARG)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditNoteCommand(INDEX_FIRST, DESC_DIARYTWO, VALID_STRING_COMMAND_ARG)));
    }

}
