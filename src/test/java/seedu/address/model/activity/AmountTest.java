package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(-0.1));
    }

    @Test
    public void isValidAmount() {
        // invalid amounts
        assertFalse(Amount.isValidAmount(0)); // Not positive
        assertFalse(Amount.isValidAmount(-1)); // Negative int
        assertFalse(Amount.isValidAmount(0.004999)); // Rounds to 0
        assertFalse(Amount.isValidAmount(-0.01)); // Negative double
        assertFalse(Amount.isValidAmount(1000000.01)); // More than 1 mil
        assertFalse(Amount.isValidAmount(1000000.005)); // Rounds to more than 1 mil

        // valid amounts
        assertTrue(Amount.isValidAmount(0.01)); // Minimum allowable
        assertTrue(Amount.isValidAmount(0.005)); // rounds to 0.01
        assertTrue(Amount.isValidAmount(1.0)); // Double in range
        assertTrue(Amount.isValidAmount(9999)); // Int in range
        assertTrue(Amount.isValidAmount(1000000.0049)); // rounds to 1 mil
        assertTrue(Amount.isValidAmount(1000000.00)); // Maximum allowable double
        assertTrue(Amount.isValidAmount(1000000)); // Maximum allowable int
    }

    @Test
    public void constructor_internalRounding() {
        // Anything within Amount.MIN and Amount.MAX is not rounded, but any valid amount beyond the range is rounded
        assertEquals(new Amount(0.005), new Amount(Amount.MIN));
        assertNotEquals(new Amount(0.005), new Amount(0.010001));

        assertEquals(new Amount(1000000.0049999), new Amount(Amount.MAX));
        assertNotEquals(new Amount(1000000.0049999), new Amount(999999.99999));
    }
}
