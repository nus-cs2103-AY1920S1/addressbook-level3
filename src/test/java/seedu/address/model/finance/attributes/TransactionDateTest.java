package seedu.address.model.finance.attributes;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TransactionDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionDate(null));
    }

    @Test
    public void constructor_invalidTransactionDate_throwsIllegalArgumentException() {
        String invalidTransactionDate = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionDate(invalidTransactionDate));
    }

    @Test
    public void isValidTransactionDate() {
        // null transaction date
        assertThrows(NullPointerException.class, () -> TransactionDate.isValidTransactionDate(null));

        // invalid transaction dates
        assertFalse(TransactionDate.isValidTransactionDate("")); // empty string
        assertFalse(TransactionDate.isValidTransactionDate(" ")); // spaces only
        assertFalse(TransactionDate.isValidTransactionDate("18/07/2019")); // backslashes
        assertFalse(TransactionDate.isValidTransactionDate("aa-bb-cccc")); // alphabets
        assertFalse(TransactionDate.isValidTransactionDate("31-02-2019")); // month without 31 days

        // valid transaction dates
        assertTrue(TransactionDate.isValidTransactionDate("2-2-2019")); // single digit
        assertTrue(TransactionDate.isValidTransactionDate("20-08-2018")); // double digit
    }
}
