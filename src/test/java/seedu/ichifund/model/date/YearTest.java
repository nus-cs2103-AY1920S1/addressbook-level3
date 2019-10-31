package seedu.ichifund.model.date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class YearTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidYear = "";
        assertThrows(IllegalArgumentException.class, () -> new Year(invalidYear));
    }

    @Test
    public void isValidYear() {
        // null year
        assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // invalid year
        assertFalse(Year.isValidYear("1999"));
        assertFalse(Year.isValidYear("10000"));
        assertFalse(Year.isValidYear("999"));
        assertFalse(Year.isValidYear(""));
        assertFalse(Year.isValidYear("abcd"));


        assertTrue(Year.isValidYear("2000"));
        assertTrue(Year.isValidYear("9999"));
        assertTrue(Year.isValidYear("3487"));
    }

    @Test
    public void isLeapYear() {
        assertFalse(new Year("2100").isLeapYear());
        assertFalse(new Year("2005").isLeapYear());
        assertFalse(new Year("2009").isLeapYear());

        assertTrue(new Year("2000").isLeapYear());
        assertTrue(new Year("2400").isLeapYear());
        assertTrue(new Year("2008").isLeapYear());
    }

    @Test
    public void compareTo() {
        assertTrue(new Year("2008").compareTo(new Year("2007")) < 0);
    }
}
