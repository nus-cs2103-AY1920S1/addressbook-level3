package seedu.address.model.expense;

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
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amounts
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("912462724523523")); // more than 12 digits
        assertFalse(Amount.isValidAmount("amount")); // non-numeric
        assertFalse(Amount.isValidAmount("9011p041")); // alphabets within digits
        assertFalse(Amount.isValidAmount("9312 1534")); // spaces within digits

        // valid amounts
        assertTrue(Amount.isValidAmount("1")); // exactly 1 digit
        assertTrue(Amount.isValidAmount("1.15")); // exactly 2 decimals
        assertTrue(Amount.isValidAmount("1472745.15")); // less than 12 digits and exactly 2 decimals
        assertTrue(Amount.isValidAmount("93121534")); // less than 12 digits
    }
}
