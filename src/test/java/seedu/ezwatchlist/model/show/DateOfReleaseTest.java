package seedu.ezwatchlist.model.show;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

public class DateOfReleaseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDateofRelease() {
        // null Date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid Date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // valid Date
        assertTrue(Date.isValidDate("24 September 1997"));
        assertTrue(Date.isValidDate("1998")); // year only
        }

}
