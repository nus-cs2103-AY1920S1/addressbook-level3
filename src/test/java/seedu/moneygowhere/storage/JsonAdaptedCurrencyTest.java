package seedu.moneygowhere.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.storage.JsonAdaptedCurrency.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.currency.Currency;

class JsonAdaptedCurrencyTest {

    private static final String INVALID_NAME = "SGDEE";
    private static final String INVALID_SYMBOL = "aaaaaaaaaaaaaaaa";
    private static final double INVALID_RATE = -1.0;

    private static final String VALID_NAME = "SGD";
    private static final String VALID_SYMBOL = "$";
    private static final double VALID_RATE = 1.0;

    private static final Currency SGD_CURRENCY = new Currency(VALID_NAME, VALID_SYMBOL, VALID_RATE);

    @Test
    public void toModelType_validCurrency_returnsCurrency() throws Exception {
        JsonAdaptedCurrency currency = new JsonAdaptedCurrency(VALID_NAME, VALID_SYMBOL, VALID_RATE);
        assertEquals(SGD_CURRENCY, currency.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCurrency currency = new JsonAdaptedCurrency(INVALID_NAME, VALID_SYMBOL, VALID_RATE);
        String expectedMessage = Currency.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, currency::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCurrency currency = new JsonAdaptedCurrency(null, VALID_SYMBOL, VALID_RATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        assertThrows(IllegalValueException.class, expectedMessage, currency::toModelType);
    }

    @Test
    public void toModelType_invalidSymbol_throwsIllegalValueException() {
        JsonAdaptedCurrency currency = new JsonAdaptedCurrency(VALID_NAME, INVALID_SYMBOL, VALID_RATE);
        String expectedMessage = Currency.MESSAGE_CONSTRAINT_SYMBOL;
        assertThrows(IllegalValueException.class, expectedMessage, currency::toModelType);
    }

    @Test
    public void toModelType_nullSymbol_throwsIllegalValueException() {
        JsonAdaptedCurrency currency = new JsonAdaptedCurrency(VALID_NAME, null, VALID_RATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Symbol");
        assertThrows(IllegalValueException.class, expectedMessage, currency::toModelType);
    }

    @Test
    public void toModelType_invalidRate_throwsIllegalValueException() {
        String expectedMessage = Currency.MESSAGE_CONSTRAINT_RATE;

        JsonAdaptedCurrency currency1 = new JsonAdaptedCurrency(VALID_NAME, VALID_SYMBOL, INVALID_RATE);
        assertThrows(IllegalValueException.class, expectedMessage, currency1::toModelType);

        JsonAdaptedCurrency currency2 = new JsonAdaptedCurrency(VALID_NAME, VALID_SYMBOL, Double.MAX_VALUE);
        assertThrows(IllegalValueException.class, expectedMessage, currency2::toModelType);
    }

    @Test
    public void test_compareTo() {
        assertEquals(0, SGD_CURRENCY.compareTo(SGD_CURRENCY));
    }
}
