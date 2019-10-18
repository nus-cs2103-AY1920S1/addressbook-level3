package seedu.address.model.note;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.Assert;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

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
