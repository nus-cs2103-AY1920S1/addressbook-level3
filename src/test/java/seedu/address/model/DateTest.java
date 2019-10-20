package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.Date;

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
        // null Date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid Date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("abc")); // alphabet only
        assertFalse(Date.isValidDate("^!")); // only non-alphanumeric characters
        assertFalse(Date.isValidDate("1921*")); // contains non-alphanumeric characters
        assertFalse(Date.isValidDate("3022019")); // not in correct format
        assertFalse(Date.isValidDate("00012019")); // not a valid date

        // valid Date
        assertTrue(Date.isValidDate("19112019")); // numerical digits only
        assertTrue(Date.isValidDate("01012069"));
    }

    @Test
    public void equals() {
        Date firstDate = new Date("21032019");
        Date secondDate = new Date("19112019");

        // same object -> returns true
        assertTrue(firstDate.equals(firstDate));

        // same values -> returns true
        assertTrue(firstDate.equals(new Date("21032019")));

        // different types -> returns false
        assertFalse(firstDate.equals(1));

        // null -> returns false
        assertFalse(firstDate.equals(null));

        // different Date -> returns false
        assertFalse(firstDate.equals(secondDate));
    }

}
