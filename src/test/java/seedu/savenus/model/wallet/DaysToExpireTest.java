package seedu.savenus.model.wallet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

public class DaysToExpireTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DaysToExpire(null));
    }

    @Test
    public void constructor_invalidDaysToExpire_throwsIllegalArgumentException() {
        String invalidDaysToExpire = "abc";
        assertThrows(IllegalArgumentException.class, () -> new DaysToExpire(invalidDaysToExpire));
    }

    @Test
    public void constructor_daysToExpireWithSpacesOnly_throwsIllegalArgumentException() {
        String invalidDaysToExpire = "                      ";
        assertThrows(IllegalArgumentException.class, () -> new  DaysToExpire(invalidDaysToExpire));
    }

    @Test
    public void isValidDaysToExpireTest() {
        // null name
        assertThrows(NullPointerException.class, () -> DaysToExpire.isValidDaysToExpire(null));

        // invalid daysToExpire
        assertFalse(DaysToExpire.isValidDaysToExpire("^")); // only non-alphanumeric characters
        assertFalse(DaysToExpire.isValidDaysToExpire("prata*")); // contains non-alphanumeric characters
        // assertFalse(DaysToExpire.isValidDaysToExpire("1234")); // too long daysToExpire
        assertFalse(DaysToExpire.isValidDaysToExpire("-10")); // negative daysToExpire

        // valid daysToExpire
        assertTrue(DaysToExpire.isValidDaysToExpire("123")); // valid daysToExpire
    }

    @Test
    public void isEmptyDaysToExpire() {
        assertFalse(DaysToExpire.isValidDaysToExpire("")); // empty string
        assertFalse(DaysToExpire.isValidDaysToExpire(" ")); // spaces only
        assertFalse(DaysToExpire.isValidDaysToExpire("           ")); // tons of spaces
    }

}
