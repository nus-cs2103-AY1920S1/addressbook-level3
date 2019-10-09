package seedu.mark.model.reminder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;

class NoteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Note(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Note.isValidName(null));

        // invalid name
        assertFalse(Note.isValidName("")); // empty string
        assertFalse(Note.isValidName(" ")); // spaces only
        assertFalse(Note.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Note.isValidName("open*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Note.isValidName("open bookmark")); // alphabets only
        assertTrue(Note.isValidName("12345")); // numbers only
        assertTrue(Note.isValidName("the 2nd bookmark")); // alphanumeric characters
        assertTrue(Note.isValidName("Take notes")); // with capital letters
        assertTrue(Note.isValidName("Click into page to do evaluation")); // long names
    }
}