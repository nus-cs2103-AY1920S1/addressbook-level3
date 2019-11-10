package seedu.address.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.finance.JsonAdaptedLogEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.RepaidDate;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.LendLogEntry;

public class JsonAdaptedLendLogEntryTest {

    private static final String INVALID_AMOUNT = "0.00";
    private static final String INVALID_TDATE = "11/11/2019";
    private static final String INVALID_DESC = " ";
    private static final String INVALID_TMET = " ";
    private static final String INVALID_FROM = " ";
    private static final String INVALID_CAT = "p@t";
    private static final String INVALID_IS_REPAID = "dang";
    private static final String INVALID_REPAID_DATE = "09-09-2019";

    private static final String VALID_AMOUNT = LOG04.getAmount().toString();
    private static final String VALID_TDATE = LOG04.getTransactionDate().toString();
    private static final String VALID_DESC = LOG04.getDescription().toString();
    private static final String VALID_TMET = LOG04.getTransactionMethod().toString();
    private static final String VALID_TO = ((LendLogEntry) LOG04).getTo().toString();
    private static final List<JsonAdaptedCategory> VALID_CATS =
            LOG04.getCategories()
                    .stream()
                    .map(JsonAdaptedCategory::new)
                    .collect(Collectors.toList());
    private static final String VALID_IS_REPAID = "true";
    private static final String VALID_REPAID_DATE = "08-11-2019";

    @Test
    public void toModelType_validLogEntryDetails_returnsLogEntry() throws Exception {
        LendLogEntry borrowLog04 = (LendLogEntry) LOG04;
        borrowLog04.markAsRepaid();
        borrowLog04.setRepaidDate(VALID_REPAID_DATE, LOG04.getTransactionDate().toString());
        JsonAdaptedLendLogEntry log = new JsonAdaptedLendLogEntry(borrowLog04);
        assertEquals(LOG04, log.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(INVALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(null, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionDate_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, INVALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = TransactionDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionDate_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, null, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, INVALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, null,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        INVALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = TransactionMethod.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        null, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionMethod.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidFrom_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), INVALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = Person.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullFrom_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), null,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCats = new ArrayList<>(VALID_CATS);
        invalidCats.add(new JsonAdaptedCategory(INVALID_CAT));
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, invalidCats, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        assertThrows(IllegalValueException.class, log::toModelType);
    }

    @Test
    public void toModelType_invalidIsRepaid_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        INVALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = "Field 'isValid' is in wrong format, should either be true or false!";
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullIsRepaid_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        null, VALID_REPAID_DATE);
        String expectedMessage = "Field 'isValid' is in wrong format, should either be true or false!";
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidRepaidDate_throwsIllegalValueException() {
        JsonAdaptedLendLogEntry log =
                new JsonAdaptedLendLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG04.getLogEntryType(), VALID_TO,
                        VALID_IS_REPAID, INVALID_REPAID_DATE);
        String expectedMessage = RepaidDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, log::toModelType);
    }

}
