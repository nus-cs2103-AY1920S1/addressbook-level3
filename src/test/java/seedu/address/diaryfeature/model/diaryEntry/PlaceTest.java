package seedu.address.diaryfeature.model.diaryEntry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.logic.parser.Validators;

public class PlaceTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Place test = new Place(null);
        assertThrows(NullPointerException.class, () -> { test.toString().length();});    }

    @Test
    public void copy_is_different_from_original() {
        Place test = new Place("Hello");
        assertFalse(test == test.copy());
    }

    @Test
    public void isValidPlace() {
        // invalid place
        assertFalse(Validators.isNotNull(null)); // null
        assertFalse(Validators.isValidPlace("")); // empty string
        assertFalse(Validators.isValidPlace("hhhhhhhhhhhhhhhhghghhghghghghghghghghghghghghghghghhghg" +
                "ghhghghghghghghghghghghghghghghghghhghghhghghghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")); // >100 chars only

        // valid place
        assertTrue(Validators.isValidPlace("Singing in the rain")); // alphabets only
        assertTrue(Validators.isValidPlace("10203")); // numbers only
        assertTrue(Validators.isValidPlace(" oompa lommpa 10203")); // alphanum
        assertTrue(Validators.isValidPlace("HELLO hi")); // Caps
        assertTrue(Validators.isValidPlace("HELLO hi hello hi 12345678910")); // <100 chars
    }
}
