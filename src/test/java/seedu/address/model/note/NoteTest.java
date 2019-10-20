package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void sameNoteTitle_consideredDuplicateNote() {
        Note note = new Note("sampleNote", "description");
        Note otherNote = new Note("sampleNote", "other description");
        assertTrue(note.isSameNote(otherNote));
    }

    @Test
    public void differentNoteTitleSameDescription_consideredUnique() {
        Note note = new Note("sampleNote", "description");
        Note otherNote = new Note("other sampleNote", "description");
        assertTrue(!note.isSameNote(otherNote));
    }
}
