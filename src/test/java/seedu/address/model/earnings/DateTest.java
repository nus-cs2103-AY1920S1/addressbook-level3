package seedu.address.model.earnings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "011/93-2933";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void constructor_notLeapYearDate_throwsIllegalArgumentException() {
        String notLeapYearDate = "29/02/2019";
        assertThrows(IllegalArgumentException.class, () -> new Date(notLeapYearDate));
    }

    @Test
    public void constructor_isLeapYearDate_throwsIllegalArgumentException() {
        String leapYearDate = "29/02/2016";
        assertTrue(Date.isValidDateNum(leapYearDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDateNum(null));

        // invalid date
        assertFalse(Date.isValidDateNum("")); // empty string
        assertFalse(Date.isValidDateNum(" ")); // spaces only
        assertFalse(Date.isValidDateNum("11-09/2020")); // different date formats mixed together

        // valid date
        assertTrue(Date.isValidDateNum("02/03/2019")); // proper date only
        assertTrue(Date.isValidDateNum("23-02-1997")); // different date format
    }
}
