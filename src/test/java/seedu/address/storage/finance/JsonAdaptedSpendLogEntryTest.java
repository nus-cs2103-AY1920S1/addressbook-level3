package seedu.address.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.finance.JsonAdaptedLogEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG01;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.SpendLogEntry;

public class JsonAdaptedSpendLogEntryTest {

    private static final String INVALID_AMOUNT = "0.00";
    private static final String INVALID_TDATE = "11/11/2019";
    private static final String INVALID_DESC = " ";
    private static final String INVALID_TMET = " ";
    private static final String INVALID_PLACE = " ";
    private static final String INVALID_CAT = "p@t";

    private static final String VALID_AMOUNT = LOG01.getAmount().toString();
    private static final String VALID_TDATE = LOG01.getTransactionDate().toString();
    private static final String VALID_DESC = LOG01.getDescription().toString();
    private static final String VALID_TMET = LOG01.getTransactionMethod().toString();
    private static final String VALID_PLACE = ((SpendLogEntry) LOG01).getPlace().toString();
    private static final List<JsonAdaptedCategory> VALID_CATS =
            LOG01.getCategories()
                    .stream()
                    .map(JsonAdaptedCategory::new)
                    .collect(Collectors.toList());

    @Test
    public void toModelType_validLogEntryDetails_returnsLogEntry() throws Exception {
        JsonAdaptedSpendLogEntry log = new JsonAdaptedSpendLogEntry(((SpendLogEntry) LOG01));
        assertEquals(LOG01, log.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(INVALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(null, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionDate_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, INVALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = TransactionDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionDate_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, null, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, VALID_TDATE, INVALID_DESC,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, VALID_TDATE, null,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        INVALID_TMET, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = TransactionMethod.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        null, VALID_CATS, LOG01.getLogEntryType(), VALID_PLACE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionMethod.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidPlace_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), INVALID_PLACE);
        String expectedMessage = Place.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullPlace_throwsIllegalValueException() {
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG01.getLogEntryType(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Place.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCats = new ArrayList<>(VALID_CATS);
        invalidCats.add(new JsonAdaptedCategory(INVALID_CAT));
        JsonAdaptedSpendLogEntry log =
                new JsonAdaptedSpendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, invalidCats, LOG01.getLogEntryType(), INVALID_PLACE);
        assertThrows(IllegalValueException.class, log::toModelType);
    }

}
