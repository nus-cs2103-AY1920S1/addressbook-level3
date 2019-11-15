package seedu.guilttrip.model.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalid = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalid));
    }

    @Test
    public void isValidAmount() {
        // null name
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid name
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount("-13.00")); // numbers which are negative
        assertFalse(Amount.isValidAmount("1.332")); // numbers which are more than 2 d.p
        assertFalse(Amount.isValidAmount("$1.33")); // numbers which contain non-numeric characters

        // valid name
        assertTrue(Amount.isValidAmount("0.01")); // numbers which have 2 d.p and is bigger than 0
        assertTrue(Amount.isValidAmount("1.0")); //  numbers which have 1 d.p and is bigger than 0
        assertTrue(Amount.isValidAmount("1")); // numbers with 0 d.p and is bigger than 0
        assertTrue(Amount.isValidAmount("9999999.99")); // Theoratical max amount allowed.
    }

    @Test
    public void isValidValue() {
        // null name
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid name
        assertFalse(Amount.isValidValue(-13.00)); // numbers which are negative
        assertFalse(Amount.isValidValue(99999999)); // numbers which are more than maximum value allowed
        assertFalse(Amount.isValidValue(0.00)); // numbers which are 0

        // valid name
        assertTrue(Amount.isValidValue(0.01)); // numbers which have 2 d.p and is bigger than 0
        assertTrue(Amount.isValidValue(15000)); // Middle value
        assertTrue(Amount.isValidValue(9999999.99)); // Theoratical max amount allowed.
    }

}
