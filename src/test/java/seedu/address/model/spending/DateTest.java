package seedu.address.model.spending;

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
        String invalidDate = " ";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date number
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // valid dates
        assertTrue(Date.isValidDate("1/1/2019"));
        assertTrue(Date.isValidDate("25/12/2019"));
        assertTrue(Date.isValidDate("today"));
        assertTrue(Date.isValidDate("yesterday"));
    }
}
