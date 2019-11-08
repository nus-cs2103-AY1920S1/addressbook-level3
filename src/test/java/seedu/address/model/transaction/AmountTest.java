package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertTrue(Amount.isWithinLimits(1000000000));
        assertTrue(Amount.isWithinLimits(999999999));
        assertTrue(Amount.isWithinLimits(0));
        assertTrue(Amount.isWithinLimits(1));
        assertTrue(Amount.isWithinLimits(-1));
        assertTrue(Amount.isWithinLimits(-Amount.UNSIGNED_INT_LIMIT));
    }

    @Test
    public void isWithinLimits_overLimits_false() {
        assertFalse(Amount.isWithinLimits(1000000001));
        assertFalse(Amount.isWithinLimits(Amount.UNSIGNED_INT_LIMIT + 1));
        assertFalse(Amount.isWithinLimits(-Amount.UNSIGNED_INT_LIMIT - 1));
    }

    @Test
    public void isValidAmount_validAmount_true() {
        assertTrue(Amount.isValidAmount(-0.52));
        assertTrue(Amount.isValidAmount(-1.30));
        assertTrue(Amount.isValidAmount(-100000.02));
        assertTrue(Amount.isValidAmount(0.50));
        assertTrue(Amount.isValidAmount(0.01));
        assertTrue(Amount.isValidAmount(12.34));
        assertTrue(Amount.isValidAmount(12.04));
        assertTrue(Amount.isValidAmount(10.20));
        assertTrue(Amount.isValidAmount(12.4));
        assertTrue(Amount.isValidAmount(1093284.0));
        assertTrue(Amount.isValidAmount(1093284.00));
    }

    @Test
    public void isValidAmount_invalidAmount_false() {
        assertFalse(Amount.isValidAmount(0.001));
        assertFalse(Amount.isValidAmount(-0.001));
        assertFalse(Amount.isValidAmount(12.043));
        assertFalse(Amount.isValidAmount(12.0401));
        assertFalse(Amount.isValidAmount(-10.2302));
        assertFalse(Amount.isValidAmount(10.999999999999999));
    }

    @Test
    public void addAmount_success() {
        assertEquals(ONE, ZERO.addAmount(ONE));
        Amount amountTwo = new Amount(2);
        assertEquals(amountTwo, ONE.addAmount(ONE));
        Amount amountNeg = new Amount(-2);
        assertEquals(ZERO, amountNeg.addAmount(amountTwo));
    }

    @Test
    public void addAmount_fail() {
        assertNotEquals(ZERO, ZERO.addAmount(ONE));
        Amount amountTwo = new Amount(2);
        assertNotEquals(ONE, ONE.addAmount(ONE));
        Amount amountNeg = new Amount(-2);
        assertNotEquals(ZERO, amountNeg.addAmount(amountNeg));
    }

    @Test
    public void subtractAmount_success() {
        assertEquals(ONE, ONE.subtractAmount(ZERO));
        Amount amountTwo = new Amount(2);
        assertEquals(ONE, amountTwo.subtractAmount(ONE));
    }

    @Test
    public void subtractAmount_fail() {
        assertNotEquals(ONE, ZERO.subtractAmount(ONE));
        Amount amountTwo = new Amount(2);
        assertNotEquals(amountTwo, ONE.subtractAmount(ONE));
    }

    @Test
    public void divideAmount_success() {
        assertEquals(1.00, ONE.divideAmount(ONE));
        Amount amountFive = new Amount(5);
        assertEquals(5.00, amountFive.divideAmount(ONE));
    }

    @Test
    public void divideAmount_fail() {
        assertNotEquals(ONE, ONE.divideAmount(ONE));
        Amount amountFive = new Amount(5);
        assertEquals(5, amountFive.divideAmount(ONE));
    }

    @Test
    public void divideAmount_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> ONE.divideAmount(ZERO));
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

    @Test
    public void makeNegative_success() {
        Amount negativeOne = new Amount(-1);
        assertEquals(negativeOne, ONE.makeNegative());
        assertEquals(negativeOne, negativeOne.makeNegative());
    }

    @Test
    public void makePositive_success() {
        Amount negativeOne = new Amount(-1);
        assertEquals(ONE, ONE.makePositive());
        assertEquals(ONE, negativeOne.makePositive());
    }

    @Test
    public void isNegative_success() {
        Amount negativeOne = new Amount(-1);
        assertTrue(negativeOne.isNegative());
        assertFalse(ONE.isNegative());
        assertFalse(ZERO.isNegative());
    }
}
