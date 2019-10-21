package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.Amount;

public class AmountTest {
    private static final Amount ZERO = new Amount(0);
    private static final Amount ONE = new Amount(1);

    @Test
    public void isValidAmountTest() {
        assertTrue(Amount.isValidAmount(12.34));
        assertTrue(Amount.isValidAmount(12.04));
        assertTrue(Amount.isValidAmount(109328402395748723242.04));
        assertFalse(Amount.isValidAmount(12.043));
        assertFalse(Amount.isValidAmount(12.0401));
    }

    @Test
    public void byShare_validShares() {
        assertEquals(ZERO, ONE.byShare(0));
        assertEquals(new Amount(10), new Amount(30).byShare(1.0 / 3));
    }

    @Test
    public void byShare_invalidShares_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> ONE.byShare(-1));
    }
}
