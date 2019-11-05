package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EndAgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EndAge(null));
    }

    @Test
    public void constructor_invalidEndAge_throwsIllegalArgumentException() {
        String invalidEndAge = "";
        assertThrows(IllegalArgumentException.class, () -> new EndAge(invalidEndAge));
    }

    @Test
    public void isValidAge() {
        // null age
        assertThrows(NullPointerException.class, () -> EndAge.isValidAge(null));

        // invalid age
        assertFalse(EndAge.isValidAge("")); // empty string
        assertFalse(EndAge.isValidAge(" ")); // spaces only
        assertFalse(EndAge.isValidAge("^")); // only non-alphanumeric characters
        assertFalse(EndAge.isValidAge("30*")); // contains non-alphanumeric characters
        assertFalse(EndAge.isValidAge("abc")); // contains alphabets
        assertFalse(EndAge.isValidAge("130")); // past the maximum age

        // valid age
        assertTrue(EndAge.isValidAge("12")); // valid prefixes and numbers
        assertTrue(EndAge.isValidAge("119")); // below the maximum age
    }
}
