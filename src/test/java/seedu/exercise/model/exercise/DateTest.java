package seedu.exercise.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

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
    public void isValidDate() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid phone numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("91")); // less than 3 numbers
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits
        assertFalse(Date.isValidDate("32/02/2019")); // A month cannot have 32 days

        // valid phone numbers
        assertTrue(Date.isValidDate("12/02/2019"));
        assertTrue(Date.isValidDate("31/10/2019"));
        assertTrue(Date.isValidDate("31/02/2019")); // Date will be round down to the latest day of that month
    }
}
