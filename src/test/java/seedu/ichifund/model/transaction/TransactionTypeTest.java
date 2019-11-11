package seedu.ichifund.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TransactionTypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionType(null));
    }

    @Test
    public void constructor_invalidTransactionType_throwsIllegalArgumentException() {
        String invalidTransactionType = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionType(invalidTransactionType));
    }

    @Test
    public void isValidTransactionType() {
        // null transaction type
        assertThrows(NullPointerException.class, () -> TransactionType.isValidTransactionType(null));

        // invalid transaction type
        assertFalse(TransactionType.isValidTransactionType("")); // empty string
        assertFalse(TransactionType.isValidTransactionType(" ")); // spaces only
        assertFalse(TransactionType.isValidTransactionType("^")); // only non-alphanumeric characters
        assertFalse(TransactionType.isValidTransactionType("dinner*")); // contains non-alphanumeric characters
        assertFalse(TransactionType.isValidTransactionType("inc")); // characters only, invalid string

        // valid transaction type
        assertTrue(TransactionType.isValidTransactionType("in"));
        assertTrue(TransactionType.isValidTransactionType("exp"));
    }
}
