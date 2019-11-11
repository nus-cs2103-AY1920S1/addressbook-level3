package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.commons.Currency;
import seedu.address.model.exchangedata.ExchangeDataSingleton;

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

        // invalid currency formats
        assertFalse(Currency.isValidCurrency("")); // empty string
        assertFalse(Currency.isValidCurrency(" ")); // spaces only
        assertFalse(Currency.isValidCurrency("X")); // Doesn't meet 3 Letter requirement
        assertFalse(Currency.isValidCurrency("XX")); // Doesn't meet 3 Letter requirement
        assertFalse(Currency.isValidCurrency("XX X")); // spaces within Currency

        // valid currency formats
        assertTrue(Currency.isValidCurrency("SGD")); // exactly SGD
        assertTrue(Currency.isValidCurrency("USD")); // exactly USD
        assertTrue(Currency.isValidCurrency("MYR")); // exactly MYR
        assertTrue(Currency.isValidCurrency("JPY")); // exactly MYR

        //invalid currency (Unsupported Currencies)
        assertFalse(ExchangeDataSingleton.getInstance().isValidCurrency("XCD")); // exactly SGD
        assertFalse(ExchangeDataSingleton.getInstance().isValidCurrency("BZD")); // exactly USD
        assertFalse(ExchangeDataSingleton.getInstance().isValidCurrency("BOV")); // exactly MYR
        assertFalse(ExchangeDataSingleton.getInstance().isValidCurrency("XAF")); // exactly MYR

        //Valid currency (Supported Currencies)
        assertTrue(ExchangeDataSingleton.getInstance().isValidCurrency("SGD")); // exactly SGD
        assertTrue(ExchangeDataSingleton.getInstance().isValidCurrency("USD")); // exactly USD
        assertTrue(ExchangeDataSingleton.getInstance().isValidCurrency("MYR")); // exactly MYR
        assertTrue(ExchangeDataSingleton.getInstance().isValidCurrency("JPY")); // exactly JPY
    }
}
