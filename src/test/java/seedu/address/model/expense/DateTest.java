package seedu.address.model.expense;

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
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidDate("12/13")); // missing year
        assertFalse(Date.isValidDate("12/2019")); // missing day
        assertFalse(Date.isValidDate("31/2019")); // missing month

        // invalid parts
        assertFalse(Date.isValidDate("123/11/2019")); // invalid day
        assertFalse(Date.isValidDate("12/13/2019")); // invalid month
        assertFalse(Date.isValidDate("12/11/20111")); // invalid year
        assertFalse(Date.isValidDate("1/1/2019 12222")); // invalid time
        assertFalse(Date.isValidDate("01/2/2019 2431")); // invalid time
        assertFalse(Date.isValidDate("02/02/2019 0")); // invalid time

        // valid date
        assertTrue(Date.isValidDate("1/1/2019"));
        assertTrue(Date.isValidDate("01/2/2019"));
        assertTrue(Date.isValidDate("02/02/2019"));
        assertTrue(Date.isValidDate("1/02/2019"));
        assertTrue(Date.isValidDate("1/1/2019 1845"));
        assertTrue(Date.isValidDate("01/2/2019 1213"));
        assertTrue(Date.isValidDate("02/02/2019 1522"));
        assertTrue(Date.isValidDate("1/02/2019 355"));
        assertTrue(Date.isValidDate("1155"));
        assertTrue(Date.isValidDate("155"));
        assertTrue(Date.isValidDate("1455"));
    }
}
