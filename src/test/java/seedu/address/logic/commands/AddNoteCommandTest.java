package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.note.AddNoteCommand;
import seedu.address.model.AppData;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.note.Note;
import seedu.address.testutil.NoteBuilder;

public class AddNoteCommandTest {
    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNoteCommand(null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Note validNote = new NoteBuilder().build();

        CommandResult commandResult = new AddNoteCommand(validNote).execute(modelStub);

        assertEquals(String.format(AddNoteCommand.MESSAGE_SUCCESS, validNote), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validNote), modelStub.notesAdded);
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note validNote = new NoteBuilder().build();
        AddNoteCommand addNoteCommand = new AddNoteCommand(validNote);
        ModelStub modelStub = new ModelStubWithNote(validNote);

        assertThrows(CommandException.class,
                AddNoteCommand.MESSAGE_DUPLICATE_TITLE, () -> addNoteCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Note alice = new NoteBuilder().withTitle("Alice").build();
        Note bob = new NoteBuilder().withTitle("Bob").build();
        AddNoteCommand addAliceCommand = new AddNoteCommand(alice);
        AddNoteCommand addBobCommand = new AddNoteCommand(bob);

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddNoteCommand addAliceCommandCopy = new AddNoteCommand(alice);
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotNull(addAliceCommand);

        // different note -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
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
     * A Model stub that always accepts the note being added.
     */
    private static class ModelStubAcceptingNoteAdded extends ModelStub {
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
        public ReadOnlyAppData getAppData() {
            return new AppData();
        }

        @Override
        public Path getAppDataFilePath() {
            return Path.of("d", "dd");
        }
    }
}
