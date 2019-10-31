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
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount - alphabetical characters
        assertFalse(Amount.isValidAmount("ab.cd"));

        // invalid amount - dollars and cents format
        assertFalse(Amount.isValidAmount("50.0"));
        assertFalse(Amount.isValidAmount("50."));
        assertFalse(Amount.isValidAmount(".05"));

        // invalid amount - leading zeroes
        assertFalse(Amount.isValidAmount("050"));
        assertFalse(Amount.isValidAmount("050.00"));
        assertFalse(Amount.isValidAmount("00.00"));

        // valid amount - positive
        assertTrue(Amount.isValidAmount("50.00"));
        assertTrue(Amount.isValidAmount("50"));
        assertTrue(Amount.isValidAmount("0.50"));
        assertTrue(Amount.isValidAmount("0.00"));
        assertTrue(Amount.isValidAmount("0"));

        // valid amount - negative
        assertTrue(Amount.isValidAmount("-49.70"));
        assertTrue(Amount.isValidAmount("-50"));
        assertTrue(Amount.isValidAmount("-0.50"));
    }

    @Test
    void testToString() {
    }

    @Test
    void getValueInCents() {
        Amount amount = new Amount("-5.70");
        assertEquals(amount.getValueInCents(), -570);

        amount = new Amount("5.70");
        assertEquals(amount.getValueInCents(), 570);
    }
}
