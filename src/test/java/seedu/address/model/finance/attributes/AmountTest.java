package seedu.address.model.finance.attributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String isInvalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(isInvalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amounts
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("-2")); // negative amount
        assertFalse(Amount.isValidAmount("-")); // dash
        assertFalse(Amount.isValidAmount("52.926")); // more than 2 decimal places
        assertFalse(Amount.isValidAmount("0")); // zero amount
        assertFalse(Amount.isValidAmount("0.0")); // zero amount
        assertFalse(Amount.isValidAmount("0.00")); // zero amount

        // valid amounts
        assertTrue(Amount.isValidAmount("12.34"));
        assertTrue(Amount.isValidAmount("0.01")); // less than a dollar
        assertTrue(Amount.isValidAmount("8")); // no decimals
        assertTrue(Amount.isValidAmount("8.3")); // 1 decimal place
        assertTrue(Amount.isValidAmount("9.03")); // 2 decimal places
        assertTrue(Amount.isValidAmount("20.00")); // 2 decimal places, with "0.00" in string
        assertTrue(Amount.isValidAmount("1234567890")); // large number
    }
}
