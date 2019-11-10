package seedu.address.model.usersettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RenewPeriodTest {
    private static final int TEST_RENEW_PERIOD = 14;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RenewPeriod(null));
    }

    @Test
    public void constructor_invalidRenewPeriod_throwsIllegalArgumentException() {
        String invalidRenewPeriod = "";
        assertThrows(IllegalArgumentException.class, () -> new RenewPeriod(invalidRenewPeriod));
    }

    @Test
    public void isValidRenewPeriod() {
        // null renewPeriod
        assertThrows(NullPointerException.class, () -> RenewPeriod.isValidRenewPeriod(null));

        // invalid renewPeriod
        assertFalse(RenewPeriod.isValidRenewPeriod("")); // empty string
        assertFalse(RenewPeriod.isValidRenewPeriod(" ")); // spaces only
        assertFalse(RenewPeriod.isValidRenewPeriod("^")); // only non-alphanumeric characters
        assertFalse(RenewPeriod.isValidRenewPeriod("hello*")); // contains non-alphanumeric characters
        assertFalse(RenewPeriod.isValidRenewPeriod("hello world")); // alphabets only
        assertFalse(RenewPeriod.isValidRenewPeriod("-1")); // negative integer
        assertFalse(RenewPeriod.isValidRenewPeriod("366")); // exceed max renew period
        assertFalse(RenewPeriod.isValidRenewPeriod("0")); // zero

        // valid renewPeriod
        assertTrue(RenewPeriod.isValidRenewPeriod("123")); // numbers only
        assertTrue(RenewPeriod.isValidRenewPeriod("365")); // max value
        assertTrue(RenewPeriod.isValidRenewPeriod("1")); // min value
    }

    @Test
    public void toString_success() {
        RenewPeriod rp = new RenewPeriod(TEST_RENEW_PERIOD);
        assertEquals(rp.toString(), String.format("%d", TEST_RENEW_PERIOD));
    }

    @Test
    public void equals() {
        RenewPeriod rp1 = new RenewPeriod(TEST_RENEW_PERIOD);
        RenewPeriod rp2 = new RenewPeriod(TEST_RENEW_PERIOD);
        RenewPeriod rp3 = new RenewPeriod(TEST_RENEW_PERIOD + 1);
        assertEquals(rp1, rp2);
        assertNotEquals(rp1, rp3);
    }

    @Test
    public void hashcode() {
        RenewPeriod rp1 = new RenewPeriod(TEST_RENEW_PERIOD);
        RenewPeriod rp2 = new RenewPeriod(TEST_RENEW_PERIOD);
        assertEquals(rp1.hashCode(), rp2.hashCode());
    }
}
