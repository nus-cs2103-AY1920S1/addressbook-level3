package seedu.mark.model.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class NoteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidNote_throwsIllegalArgumentException() {
        String invalidNote = "";
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidNote));
    }

    @Test
    public void isValidNote() {
        // null remark
        assertThrows(NullPointerException.class, () -> Note.isValidNote(null));

        // invalid notes
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only
        assertFalse(Note.isValidNote(" r/r")); // contains forward slash

        // valid notes
        assertTrue(Note.isValidNote("Blk 456, Den Road, #01-355"));
        assertTrue(Note.isValidNote("-")); // one character
        assertTrue(Note.isValidNote("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long Note
    }

    @Test
    public void isEmptyNote() {
        // null Note
        assertThrows(NullPointerException.class, () -> Note.isEmptyNote(null));

        // empty notes
        assertTrue(Note.isEmptyNote("")); // empty string
        assertTrue(Note.isEmptyNote(" ")); // spaces only

        // non-empty note
        assertFalse(Note.isEmptyNote("abc"));
    }
}
