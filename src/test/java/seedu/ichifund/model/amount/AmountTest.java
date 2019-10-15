package seedu.ichifund.model.amount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("50.0"));
        assertFalse(Amount.isValidAmount("50."));
        assertFalse(Amount.isValidAmount(".05"));
        assertFalse(Amount.isValidAmount("050"));
        assertFalse(Amount.isValidAmount("050.00"));
        assertFalse(Amount.isValidAmount("00.00"));

        // valid amount
        assertTrue(Amount.isValidAmount("50.00"));
        assertTrue(Amount.isValidAmount("-49.70"));
        assertTrue(Amount.isValidAmount("50"));
        assertTrue(Amount.isValidAmount("-50"));
        assertTrue(Amount.isValidAmount("-0.50"));
        assertTrue(Amount.isValidAmount("0.50"));
        assertTrue(Amount.isValidAmount("0.00"));
        assertTrue(Amount.isValidAmount("0"));
    }

    @Test
    void testToString() {
    }

    @Test
    void getValueInCents() {
        Amount amount = new Amount("-5.70");
        assertEquals(amount.getValueInCents(), -570);
    }
}
