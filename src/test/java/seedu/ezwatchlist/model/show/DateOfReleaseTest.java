package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateOfReleaseTest {
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

    @Test
    public void hashCode_test() {
        assertEquals(new Date("1/2/2019").hashCode(), "1/2/2019".hashCode());
    }
}
