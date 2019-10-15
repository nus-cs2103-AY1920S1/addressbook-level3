package seedu.ichifund.model;

import org.junit.jupiter.api.Test;
import seedu.ichifund.model.transaction.Amount;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(Amount.isValidAmount("50.0"), false);
        assertEquals(Amount.isValidAmount("50."), false);
        assertEquals(Amount.isValidAmount(".05"), false);
        assertEquals(Amount.isValidAmount("050"), false);
        assertEquals(Amount.isValidAmount("050.00"), false);
        assertEquals(Amount.isValidAmount("00.00"), false);

        // valid amount
        assertEquals(Amount.isValidAmount("50.00"), true);
        assertEquals(Amount.isValidAmount("-49.70"), true);
        assertEquals(Amount.isValidAmount("50"), true);
        assertEquals(Amount.isValidAmount("-50"), true);
        assertEquals(Amount.isValidAmount("-0.50"), true);
        assertEquals(Amount.isValidAmount("0.50"), true);
        assertEquals(Amount.isValidAmount("0.00"), true);
        assertEquals(Amount.isValidAmount("0"), true);
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