package seedu.address.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.finance.JsonAdaptedLogEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.IncomeLogEntry;

public class JsonAdaptedIncomeLogEntryTest {

    private static final String INVALID_AMOUNT = "0.00";
    private static final String INVALID_TDATE = "11/11/2019";
    private static final String INVALID_DESC = " ";
    private static final String INVALID_TMET = " ";
    private static final String INVALID_FROM = " ";
    private static final String INVALID_CAT = "p@t";

    private static final String VALID_AMOUNT = LOG02.getAmount().toString();
    private static final String VALID_TDATE = LOG02.getTransactionDate().toString();
    private static final String VALID_DESC = LOG02.getDescription().toString();
    private static final String VALID_TMET = LOG02.getTransactionMethod().toString();
    private static final String VALID_FROM = ((IncomeLogEntry) LOG02).getFrom().toString();
    private static final List<JsonAdaptedCategory> VALID_CATS =
            LOG02.getCategories()
                    .stream()
                    .map(JsonAdaptedCategory::new)
                    .collect(Collectors.toList());

    @Test
    public void toModelType_validLogEntryDetails_returnsLogEntry() throws Exception {
        JsonAdaptedIncomeLogEntry log = new JsonAdaptedIncomeLogEntry(((IncomeLogEntry) LOG02));
        assertEquals(LOG02, log.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(INVALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(null, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionDate_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, INVALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = TransactionDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionDate_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, null, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, VALID_TDATE, INVALID_DESC,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, VALID_TDATE, null,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        INVALID_TMET, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = TransactionMethod.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        null, VALID_CATS, LOG02.getLogEntryType(), VALID_FROM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionMethod.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidFrom_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), INVALID_FROM);
        String expectedMessage = Person.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullFrom_throwsIllegalValueException() {
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG02.getLogEntryType(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCats = new ArrayList<>(VALID_CATS);
        invalidCats.add(new JsonAdaptedCategory(INVALID_CAT));
        JsonAdaptedIncomeLogEntry log =
                new JsonAdaptedIncomeLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, invalidCats, LOG02.getLogEntryType(), VALID_FROM);
        assertThrows(IllegalValueException.class, log::toModelType);
    }

}
