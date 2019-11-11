package seedu.address.model.earnings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "22.123";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void constructor_invalidAmountWithDollarSign_throwsIllegalArgumentException() {
        String invalidAmount = "$22.12";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void constructor_negativeAmount_throwsIllegalArgumentException() {
        String negativeAmount = "-22.12";
        assertThrows(IllegalArgumentException.class, () -> new Amount(negativeAmount));
    }

    @Test
    public void isValidAmount() {
        // null name
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid name
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("fifty dollars")); // letter amount characters
        assertFalse(Amount.isValidAmount("$123.22")); // contains dollar sign characters

        // valid name
        assertTrue(Amount.isValidAmount("0")); // zero only
        assertTrue(Amount.isValidAmount("12345.23")); // numbers only
        assertTrue(Amount.isValidAmount("999999.99")); // max value
    }
}
