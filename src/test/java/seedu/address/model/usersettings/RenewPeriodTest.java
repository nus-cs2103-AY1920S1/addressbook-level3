package seedu.address.model.usersettings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RenewPeriodTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RenewPeriod(null));
    }

    @Test
    public void constructor_invalidRenewPeriod_throwsIllegalArgumentException() {
        String invalidRenewPeriod = "";
        assertThrows(IllegalArgumentException.class, () -> new LoanPeriod(invalidRenewPeriod));
    }

    @Test
    public void isValidRenewPeriod() {
        // null renewPeriod
        assertThrows(NullPointerException.class, () -> RenewPeriod.isValidRenewPeriod(null));

        // invalid loanPeriod
        assertFalse(RenewPeriod.isValidRenewPeriod("")); // empty string
        assertFalse(RenewPeriod.isValidRenewPeriod(" ")); // spaces only
        assertFalse(RenewPeriod.isValidRenewPeriod("^")); // only non-alphanumeric characters
        assertFalse(RenewPeriod.isValidRenewPeriod("hello*")); // contains non-alphanumeric characters
        assertFalse(RenewPeriod.isValidRenewPeriod("hello world")); // alphabets only
        assertFalse(RenewPeriod.isValidRenewPeriod("-1")); // negative integer

        // valid renewPeriod
        assertTrue(RenewPeriod.isValidRenewPeriod("12345")); // numbers only
    }
}
