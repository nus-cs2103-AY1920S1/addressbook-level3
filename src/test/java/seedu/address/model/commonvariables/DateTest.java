package seedu.address.model.commonvariables;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "31-31-31";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }


    @Test
    void isValidDate() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate(""));
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("30/12/2019")); // wrong format
        assertFalse(Date.isValidDate("12-25-2019")); // day and month in wrong positions

        // valid dates
        assertTrue(Date.isValidDate("30-12-2019")); // exactly 3 numbers
        assertTrue(Date.isValidDate("25-12-2019")); // exactly 3 numbers
    }
}
