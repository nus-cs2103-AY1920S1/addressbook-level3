package seedu.address.storage;

import static seedu.address.model.ModelTestUtil.VALID_NAME_CURRENCY_1;
import static seedu.address.model.ModelTestUtil.VALID_RATE_CURRENCY_1;
import static seedu.address.model.ModelTestUtil.VALID_SYMBOL_CURRENCY_1;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;

class JsonAdaptedCurrencyTest {
    //Blank Name
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws ParseException {
        JsonAdaptedCurrency jsonAdaptedCurrency = new JsonAdaptedCurrency("",
                VALID_SYMBOL_CURRENCY_1,
                Double.parseDouble(VALID_RATE_CURRENCY_1));
        String expectedMessage = seedu.address.model.itinerary.Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedCurrency::toModelType);
    }

    @Test
    public void toModelType_invalidSymbol_throwsIllegalValueException() throws ParseException {
        JsonAdaptedCurrency jsonAdaptedCurrency = new JsonAdaptedCurrency(VALID_NAME_CURRENCY_1,
                " +",
                Double.parseDouble(VALID_RATE_CURRENCY_1));
        String expectedMessage = seedu.address.model.currency.Symbol.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedCurrency::toModelType);
    }

    @Test
    public void toModelType_invalidRate_throwsIllegalValueException() throws ParseException {
        JsonAdaptedCurrency jsonAdaptedCurrency = new JsonAdaptedCurrency(VALID_NAME_CURRENCY_1,
                VALID_SYMBOL_CURRENCY_1,
                3.1415);
        String expectedMessage = seedu.address.model.currency.Rate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedCurrency::toModelType);
    }

}
