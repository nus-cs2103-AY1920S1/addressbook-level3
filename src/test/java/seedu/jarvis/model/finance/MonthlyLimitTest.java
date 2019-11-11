package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MonthlyLimitTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MonthlyLimit(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new MonthlyLimit(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        //null amount
        assertThrows(NullPointerException.class, () -> MonthlyLimit.isValidAmount(null));

        //invalid amounts
        assertFalse(MonthlyLimit.isValidAmount("")); // empty string
        assertFalse(MonthlyLimit.isValidAmount(" ")); // spaces only
        assertFalse(MonthlyLimit.isValidAmount("9011p041")); // alphabets within digits

        //valid amounts
        assertTrue(MonthlyLimit.isValidAmount("13.50"));
        assertTrue(MonthlyLimit.isValidAmount("13"));
    }

    @Test
    public void isEquals() {
        MonthlyLimit amount1 = new MonthlyLimit("5.0");
        MonthlyLimit amount2 = new MonthlyLimit("5.0");
        assertTrue(amount1.equals(amount2));
    }
}
