package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FlagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Flag(null));
    }

    @Test
    public void isValidFlag() {
        // null flag
        assertThrows(NullPointerException.class, () -> Flag.isValidFlag(null));

        // invalid flag
        assertFalse(Flag.isValidFlag("")); // empty string
        assertFalse(Flag.isValidFlag(" ")); // spaces only
        assertFalse(Flag.isValidFlag("-person")); // should be -p only
        assertFalse(Flag.isValidFlag("-event")); // should be -e only

        // valid flag
        assertTrue(Flag.isValidFlag("-p")); // person flag
        assertTrue(Flag.isValidFlag("-e")); // event flag
    }
}
