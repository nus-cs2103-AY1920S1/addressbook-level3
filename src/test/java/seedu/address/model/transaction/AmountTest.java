package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class AmountTest {
    private static final Amount ZERO = new Amount(0);
    private static final Amount ONE = new Amount(1);

    @Test
    public void amountConstructor_overBoundary_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Amount(Amount.UNSIGNED_INT_LIMIT + 1));
    }

    @Test
    public void isWithinLimits_withinLimits_true() {
        assertTrue(Amount.isWithinLimits(Amount.UNSIGNED_INT_LIMIT));
        assertTrue(Amount.isWithinLimits(0));
        assertTrue(Amount.isWithinLimits(1));
        assertTrue(Amount.isWithinLimits(-1));
        assertTrue(Amount.isWithinLimits(-Amount.UNSIGNED_INT_LIMIT));
    }

    @Test
    public void isWithinLimits_overLimits_false() {
        assertFalse(Amount.isWithinLimits(Amount.UNSIGNED_INT_LIMIT + 1));
        assertFalse(Amount.isWithinLimits(-Amount.UNSIGNED_INT_LIMIT - 1));
    }

    @Test
    public void isValidAmount_validAmount_true() {
        assertTrue(Amount.isValidAmount(12.34));
        assertTrue(Amount.isValidAmount(12.04));
        assertTrue(Amount.isValidAmount(10.20));
        assertTrue(Amount.isValidAmount(12.4));
        assertTrue(Amount.isValidAmount(1093284.0));
        assertTrue(Amount.isValidAmount(1093284.00));
    }

    @Test
    public void isValidAmount_invalidAmount_false() {
        assertFalse(Amount.isValidAmount(12.043));
        assertFalse(Amount.isValidAmount(12.0401));
        assertFalse(Amount.isValidAmount(10.999999999999999));
    }

    @Test
    public void byShare_validShares_success() {
        assertEquals(ZERO, ONE.byShare(0));
        assertEquals(new Amount(10), new Amount(30).byShare(1.0 / 3));
    }

    @Test
    public void byShare_invalidShares_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> ONE.byShare(-1));
    }
}
