package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Mode(null));
    }

    @Test
    public void constructor_invalidMode_throwsIllegalArgumentException() {
        String invalidMode = "";
        assertThrows(IllegalArgumentException.class, () -> new Mode(invalidMode));
    }

    @Test
    public void isValidMode() {
        // null mode
        assertThrows(NullPointerException.class, () -> Mode.isValidMode(null));

        // invalid mode
        assertFalse(Mode.isValidMode("")); // empty string
        assertFalse(Mode.isValidMode(" ")); // spaces only
        assertFalse(Mode.isValidMode("expense")); // non-inside pool
        assertFalse(Mode.isValidMode("9011p041")); // alphabets within digits
        assertFalse(Mode.isValidMode("category budget")); // spaces character between
        assertFalse(Mode.isValidMode("9312 1534")); // digits with spaces
        assertFalse(Mode.isValidMode("category.")); // special characters

        // valid modes
        assertTrue(Mode.isValidMode("category"));
        assertTrue(Mode.isValidMode("CATEGORY"));
        assertTrue(Mode.isValidMode("CaTeGoRY"));
        assertTrue(Mode.isValidMode("budget"));
        assertTrue(Mode.isValidMode("BUDGET"));
        assertTrue(Mode.isValidMode("buDget"));

    }
}
