package seedu.address.financialtracker.model.expense;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsAssertionError() {
        String invalidDate = "";
        assertThrows(AssertionError.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("27-10-2016")); // with dashes only
        assertFalse(Date.isValidDate("15159999")); // off-world
        assertFalse(Date.isValidDate("271016"));

        // valid dates
        assertTrue(Date.isValidDate("27102016"));
    }

}