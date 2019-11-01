package seedu.address.logic.commands.note;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_NOTE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.note.Note;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.note.NoteBuilder;

public class NoteAddCommandTest {

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NoteAddCommand(null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Note validNote = new NoteBuilder().build();

        CommandResult commandResult = new NoteAddCommand(validNote).execute(modelStub);

        assertEquals(String.format(NoteAddCommand.MESSAGE_SUCCESS, validNote), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note validNote = new NoteBuilder().build();
        NoteAddCommand addCommand = new NoteAddCommand(validNote);
        ModelStub modelStub = new ModelStubWithNote(validNote);
        assertThrows(CommandException.class, () -> addCommand.execute(modelStub), MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void equals() {
        Note note = new NoteBuilder().withNote("Note").build();
        Note otherNote = new NoteBuilder().withNote("Other Note").build();
        NoteAddCommand addNoteCommand = new NoteAddCommand(note);
        NoteAddCommand addOtherNoteCommand = new NoteAddCommand(otherNote);

        // same object -> returns true
        assertTrue(addNoteCommand.equals(addNoteCommand));

        // same values -> returns true
        NoteAddCommand addNoteCommandCopy = new NoteAddCommand(note);
        assertTrue(addNoteCommand.equals(addNoteCommandCopy));

        // different types -> returns false
        assertFalse(addNoteCommand.equals(1));

        // null -> returns false
        assertFalse(addNoteCommand.equals(null));

        // different note -> returns false
        assertFalse(addNoteCommand.equals(addOtherNoteCommand));
    }

    /**
     * A Model stub that contains a single note.
     */
    private class ModelStubWithNote extends ModelStub {
        private final Note note;

        ModelStubWithNote(Note note) {
            requireNonNull(note);
            this.note = note;
        }

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return this.note.isSameNote(note);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingNoteAdded extends ModelStub {
        final ArrayList<Note> notesAdded = new ArrayList<>();

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return notesAdded.stream().anyMatch(note::isSameNote);
        }

        @Override
        public void addNote(Note note) {
            requireNonNull(note);
            notesAdded.add(note);
        }

        @Override
        public ReadOnlyNotesRecord getNotesRecord() {
            return new NotesRecord();
        }
    }

}
