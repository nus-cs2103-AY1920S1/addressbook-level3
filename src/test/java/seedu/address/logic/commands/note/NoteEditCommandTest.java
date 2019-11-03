package seedu.address.logic.commands.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_NOTE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THREE;
import static seedu.address.testutil.TypicalIndexes.INDEX_TWO;
import static seedu.address.testutil.note.TypicalNotes.getTypicalNotesRecord;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.note.NoteEditCommand.EditNoteDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.note.Note;
import seedu.address.model.note.Priority;
import seedu.address.testutil.note.NoteBuilder;

/**
 * Contains integration tests.
 * (interaction with the Model and EditNoteDescriptor) and tests for NoteEditCommand.
 * Note that this test class has some dependency on the {@code EditNoteDescriptor}.
 */
public class NoteEditCommandTest {

    private Model model = new ModelManager();

    public NoteEditCommandTest() {
        model.setNotesRecord(getTypicalNotesRecord());
    }

    @Test
    public void execute_editAllFields_success() {
        Note editedNote = new NoteBuilder().build();
        EditNoteDescriptor descriptor = new EditNoteDescriptor();
        descriptor.setNote(Optional.of(editedNote.getNote()));
        descriptor.setDescription(Optional.of(editedNote.getDescription()));
        descriptor.setPriority(Optional.of(editedNote.getPriority()));

        NoteEditCommand editCommand = new NoteEditCommand(INDEX_ONE, descriptor);

        String expectedMessage = String.format(NoteEditCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);
        Model expectedModel = new ModelManager();
        expectedModel.setNotesRecord(getTypicalNotesRecord());
        expectedModel.setNote(model.getFilteredNotesList().get(0), editedNote);

        assertCommandSuccess(editCommand, model, new CommandResult(expectedMessage) , expectedModel);
    }

    @Test
    public void execute_editSomeFields_success() {
        Index indexLastNote = Index.fromOneBased(model.getFilteredNotesList().size());
        Note lastNote = model.getFilteredNotesList().get(indexLastNote.getZeroBased());

        NoteBuilder noteInList = new NoteBuilder(lastNote);
        Note editedNote = noteInList.withNote("changed note title").withPriority(Priority.HIGH).build();

        EditNoteDescriptor descriptor = new EditNoteDescriptor();
        descriptor.setNote(Optional.of(editedNote.getNote()));
        descriptor.setPriority(Optional.of(editedNote.getPriority()));

        NoteEditCommand editCommand = new NoteEditCommand(indexLastNote, descriptor);
        String expectedMessage = String.format(NoteEditCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager();
        expectedModel.setNotesRecord(getTypicalNotesRecord());
        expectedModel.setNote(lastNote, editedNote);

        assertCommandSuccess(editCommand, model, new CommandResult(expectedMessage), expectedModel);
    }

    @Test
    public void execute_editNoFields_throwsCommandException() {
        NoteEditCommand editCommand = new NoteEditCommand(INDEX_ONE, new NoteEditCommand.EditNoteDescriptor());
        assertThrows(CommandException.class, () -> editCommand.execute(model), NoteEditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_duplicateNewNote_throwsCommandException() {
        Note noteInList = model.getNotesRecord().getNotesList().get(INDEX_TWO.getZeroBased());
        EditNoteDescriptor descriptor = new EditNoteDescriptor();
        descriptor.setNote(Optional.of(noteInList.getNote()));
        descriptor.setDescription(Optional.of(noteInList.getDescription()));
        descriptor.setPriority(Optional.of(noteInList.getPriority()));
        NoteEditCommand editCommand = new NoteEditCommand(INDEX_ONE, descriptor);
        assertThrows(CommandException.class, () -> editCommand.execute(model), MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void execute_invalidNoteIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredNotesList().size() + 1);
        EditNoteDescriptor descriptor = new EditNoteDescriptor();
        descriptor.setDescription(Optional.of("valid description"));
        NoteEditCommand editCommand = new NoteEditCommand(outOfBoundIndex, descriptor);
        assertThrows(CommandException.class, () -> editCommand.execute(model), MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditNoteDescriptor standardDescriptor = new NoteEditCommand.EditNoteDescriptor();
        standardDescriptor.setNote(Optional.of("a standard title"));
        final NoteEditCommand standardCommand = new NoteEditCommand(INDEX_ONE, standardDescriptor);

        final EditNoteDescriptor standardDescriptorCopy = new NoteEditCommand.EditNoteDescriptor();
        standardDescriptorCopy.setNote(Optional.of("a standard title"));
        NoteEditCommand standardCommandCopy = new NoteEditCommand(INDEX_ONE, standardDescriptorCopy);

        //same content -> returns true
        assertTrue(standardCommand.equals(standardCommandCopy));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new NoteListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NoteEditCommand(INDEX_THREE, standardDescriptor)));

        // different descriptor -> returns false
        final EditNoteDescriptor differentDescriptor = new NoteEditCommand.EditNoteDescriptor();
        differentDescriptor.setNote(Optional.of("a different title"));
        assertFalse(standardCommand.equals(new NoteEditCommand(INDEX_ONE, differentDescriptor)));
    }
}
