package seedu.address.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExpiryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpiryDate(null));
    }

    @Test
    public void constructor_invalidExpiryDate_throwsIllegalArgumentException() {
        String invalidExpiryDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ExpiryDate(invalidExpiryDate));
    }

    @Test
    public void isValidExpiryDate() {
        // null expiry date
        assertThrows(NullPointerException.class, () -> ExpiryDate.isValidExpiryDate(null));

        // blank expiry date
        assertFalse(ExpiryDate.isValidExpiryDate("")); // empty string
        assertFalse(ExpiryDate.isValidExpiryDate(" ")); // spaces only

        // missing parts
        assertFalse(ExpiryDate.isValidExpiryDate("03/2019")); // missing day
        assertFalse(ExpiryDate.isValidExpiryDate("02-03")); // missing year

        // invalid parts
        assertFalse(ExpiryDate.isValidExpiryDate("10/21/2019")); // invalid month
        assertFalse(ExpiryDate.isValidExpiryDate("29/02/2017")); // invalid date
        assertFalse(ExpiryDate.isValidExpiryDate("03 /05/2008")); // contain spaces
        assertFalse(ExpiryDate.isValidExpiryDate(" 03/05/2019")); // leading space
        assertFalse(ExpiryDate.isValidExpiryDate("10/10/2019 ")); // trailing space
        assertFalse(ExpiryDate.isValidExpiryDate("02-03-2019.")); // trailing period

        // wrong format
        assertFalse(ExpiryDate.isValidExpiryDate("12/28/2019")); // wrong format
        assertFalse(ExpiryDate.isValidExpiryDate("2019/12/28")); // wrong format
        assertFalse(ExpiryDate.isValidExpiryDate("12-28-2019")); // wrong format
        assertFalse(ExpiryDate.isValidExpiryDate("2019.12.28")); // wrong format

        // valid expiry date
        assertTrue(ExpiryDate.isValidExpiryDate("20-02-2019")); // hyphen format
        assertTrue(ExpiryDate.isValidExpiryDate("03/05/2008")); // forward slash format
        assertTrue(ExpiryDate.isValidExpiryDate("05.08.2018")); // period format
    }
}
