package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TransactionDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionDate(null));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidValue = "";
        assertThrows(IllegalArgumentException.class, () -> new TransactionDate(invalidValue));
    }

    @Test
    public void isValidDate() {
        // null value
        assertThrows(NullPointerException.class, () -> TransactionDate.isValidDate(null));

        // invalid value
        assertFalse(TransactionDate.isValidDate("")); // empty string
        assertFalse(TransactionDate.isValidDate(" ")); // spaces only
        assertFalse(TransactionDate.isValidDate("^")); // only non-numeric characters
        assertFalse(TransactionDate.isValidDate("35/01/2000")); //invalid day
        assertFalse(TransactionDate.isValidDate("10/13/2000")); // invalid month

        // valid value
        assertTrue(TransactionDate.isValidDate("10/01/2000")); //proper date
    }

}
