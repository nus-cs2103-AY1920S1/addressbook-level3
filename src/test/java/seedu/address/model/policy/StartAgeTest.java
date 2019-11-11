package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StartAgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartAge(null));
    }

    @Test
    public void constructor_invalidStartAge_throwsIllegalArgumentException() {
        String invalidStartAge = "";
        assertThrows(IllegalArgumentException.class, () -> new StartAge(invalidStartAge));
    }

    @Test
    public void isValidAge() {
        // null age
        assertThrows(NullPointerException.class, () -> StartAge.isValidAge(null));

        // invalid age
        assertFalse(StartAge.isValidAge("")); // empty string
        assertFalse(StartAge.isValidAge(" ")); // spaces only
        assertFalse(StartAge.isValidAge("^")); // only non-alphanumeric characters
        assertFalse(StartAge.isValidAge("30*")); // contains non-alphanumeric characters
        assertFalse(StartAge.isValidAge("abc")); // contains alphabets
        assertFalse(StartAge.isValidAge("130")); // past the maximum age

        // valid age
        assertTrue(StartAge.isValidAge("12")); // valid prefixes and numbers
        assertTrue(StartAge.isValidAge("119")); // below the maximum age
    }
}
