package seedu.address.model.finance.attributes;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TransactionMethodTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionMethod(null));
    }

    @Test
    public void constructor_invalidTransactionMethod_throwsIllegalArgumentException() {
        String invalidTransactionMethod = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionMethod(invalidTransactionMethod));
    }

    @Test
    public void isValidTransactionMethod() {
        // null transaction method
        assertThrows(NullPointerException.class, () -> TransactionMethod.isValidTransactionMet(null));

        // invalid transaction methods
        assertFalse(TransactionMethod.isValidTransactionMet("")); // empty string
        assertFalse(TransactionMethod.isValidTransactionMet(" ")); // spaces only
        assertFalse(TransactionMethod.isValidTransactionMet("^")); // only non-alphanumeric characters
        assertFalse(TransactionMethod.isValidTransactionMet("nets*")); // contains non-alphanumeric characters
        assertFalse(TransactionMethod.isValidTransactionMet("12345")); // numbers only

        // valid transaction methods
        assertTrue(TransactionMethod.isValidTransactionMet("cash"));
        assertTrue(TransactionMethod.isValidTransactionMet("-")); // dash
        assertTrue(TransactionMethod.isValidTransactionMet("M")); // one character
        assertTrue(TransactionMethod.isValidTransactionMet("Bank credit")); // whitespace in the middle
        assertTrue(TransactionMethod.isValidTransactionMet("NETSFlashpay")); // fix of upper and lower cases
    }
}
