package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CurrencyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Currency(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidCurrency = "";
        assertThrows(IllegalArgumentException.class, () -> new Currency(invalidCurrency));
    }

    @Test
    public void isValidCurrency() {
        // null amount
        assertThrows(NullPointerException.class, () -> Currency.isValidCurrency(null));

        // invalid amounts
        assertFalse(Currency.isValidCurrency("")); // empty string
        assertFalse(Currency.isValidCurrency(" ")); // spaces only
        assertFalse(Currency.isValidCurrency("X")); // Doesn't meet 3 Letter requirement
        assertFalse(Currency.isValidCurrency("XX")); // Doesn't meet 3 Letter requirement
        assertFalse(Currency.isValidCurrency("XX X")); // spaces within Currency

        // valid amounts
        assertTrue(Currency.isValidCurrency("SGD")); // exactly SGD
        assertTrue(Currency.isValidCurrency("USD")); // exactly USD
        assertTrue(Currency.isValidCurrency("MYR")); // exactly MYR
    }
}
