package seedu.address.financialtracker.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("3.100")); // more than 2 decimals
        assertFalse(Amount.isValidAmount("3.")); // dot without decimals

        // valid amount
        assertTrue(Amount.isValidAmount("3.1"));
        assertTrue(Amount.isValidAmount("00000000000000000003.1"));
        assertTrue(Amount.isValidAmount("3.01")); // 2 decimals
        assertTrue(Amount.isValidAmount(String.valueOf(Integer.MAX_VALUE))); // HugeAmount
    }
}