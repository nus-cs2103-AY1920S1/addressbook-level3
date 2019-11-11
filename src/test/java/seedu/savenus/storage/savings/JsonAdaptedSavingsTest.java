package seedu.savenus.storage.savings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.storage.savings.JsonAdaptedSavings.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.savings.Savings;

//@@author fatclarence
public class JsonAdaptedSavingsTest {

    private static final String VALID_TIMESTAMP_STRING = "1570976664360";
    private static final String VALID_MONEY_STRING = "35.00";

    @Test
    public void toModelType_validSavings_returnsSavings() throws Exception {
        JsonAdaptedSavings savings = new JsonAdaptedSavings(
                new Savings(VALID_MONEY_STRING, VALID_TIMESTAMP_STRING, false));
        assertEquals(new Savings(VALID_MONEY_STRING, VALID_TIMESTAMP_STRING, false),
                savings.toModelType());
    }

    @Test
    public void toModelType_nullTimeStamp_throwsIllegalValueException() {
        JsonAdaptedSavings savings = new JsonAdaptedSavings(VALID_MONEY_STRING, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Savings.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, savings::toModelType);
    }
}
