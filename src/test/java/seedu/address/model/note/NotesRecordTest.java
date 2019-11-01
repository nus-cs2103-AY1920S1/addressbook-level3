package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.note.TypicalNotes.getTypicalNotesRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.note.exceptions.DuplicateNoteException;
import seedu.address.testutil.note.NoteBuilder;

public class NotesRecordTest {

    private final NotesRecord notesRecord = new NotesRecord();
    private final Note note = new NoteBuilder().withNote("note1").withDesc("desc").build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), notesRecord.getNotesList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> notesRecord.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNotesRecord_replacesData() {
        NotesRecord newData = getTypicalNotesRecord();
        notesRecord.resetData(newData);
        assertEquals(newData, notesRecord);
    }

    @Test
    public void resetData_withDuplicateNotes_throwsDuplicateNoteException() {
        Note editedNote = new NoteBuilder(note).withDesc("changed desc").build();
        List<Note> newNotes = Arrays.asList(note, editedNote);
        NotesRecordTest.NotesRecordStub newData = new NotesRecordTest.NotesRecordStub(newNotes);
        assertThrows(DuplicateNoteException.class, () -> notesRecord.resetData(newData));
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> notesRecord.hasNote(null));
    }

    @Test
    public void hasNote_noteNotInNotesRecord_returnsFalse() {
        assertFalse(notesRecord.hasNote(note));
    }

    @Test
    public void hasNotes_noteInNotesRecord_returnsTrue() {
        notesRecord.addNote(note);
        assertTrue(notesRecord.hasNote(note));
    }

    @Test
    public void hasNote_noteWithSameIdentityFieldsInNotesRecord_returnsTrue() {
        notesRecord.addNote(note);
        Note editedNote = new NoteBuilder(note).withDesc("other desc").build();
        assertTrue(notesRecord.hasNote(editedNote));
    }

    @Test
    public void getNotesList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> notesRecord.getNotesList().remove(0));
    }

    /**
     * A stub ReadOnlyNotesRecord whose notes list can violate interface constraints.
     */
    private static class NotesRecordStub implements ReadOnlyNotesRecord {
        private final ObservableList<Note> notes = FXCollections.observableArrayList();

        NotesRecordStub(Collection<Note> notes) {
            this.notes.setAll(notes);
        }

        @Override
        public ObservableList<Note> getNotesList() {
            return notes;
        }
    }

}
