package seedu.address.storage.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.finance.JsonAdaptedLogEntry.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLogEntries.LOG03;

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
import seedu.address.model.finance.logentry.BorrowLogEntry;

public class JsonAdaptedBorrowLogEntryTest {

    private static final String INVALID_AMOUNT = "0.00";
    private static final String INVALID_TDATE = "11/11/2019";
    private static final String INVALID_DESC = " ";
    private static final String INVALID_TMET = " ";
    private static final String INVALID_FROM = " ";
    private static final String INVALID_CAT = "p@t";
    private static final String INVALID_IS_REPAID = "dang";
    private static final String INVALID_REPAID_DATE = "09-09-2019";

    private static final String VALID_AMOUNT = LOG03.getAmount().toString();
    private static final String VALID_TDATE = LOG03.getTransactionDate().toString();
    private static final String VALID_DESC = LOG03.getDescription().toString();
    private static final String VALID_TMET = LOG03.getTransactionMethod().toString();
    private static final String VALID_FROM = ((BorrowLogEntry) LOG03).getFrom().toString();
    private static final List<JsonAdaptedCategory> VALID_CATS =
            LOG03.getCategories()
                    .stream()
                    .map(JsonAdaptedCategory::new)
                    .collect(Collectors.toList());
    private static final String VALID_IS_REPAID = "true";
    private static final String VALID_REPAID_DATE = "08-11-2019";

    @Test
    public void toModelType_validLogEntryDetails_returnsLogEntry() throws Exception {
        BorrowLogEntry borrowLog03 = (BorrowLogEntry) LOG03;
        borrowLog03.markAsRepaid();
        borrowLog03.setRepaidDate(VALID_REPAID_DATE, LOG03.getTransactionDate().toString());
        JsonAdaptedBorrowLogEntry log = new JsonAdaptedBorrowLogEntry(borrowLog03);
        assertEquals(LOG03, log.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(INVALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(null, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionDate_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, INVALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = TransactionDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionDate_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, null, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, INVALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, null,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        INVALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = TransactionMethod.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullTransactionMethod_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        null, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                TransactionMethod.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidFrom_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), INVALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = Person.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullFrom_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), null,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidCategories_throwsIllegalValueException() {
        List<JsonAdaptedCategory> invalidCats = new ArrayList<>(VALID_CATS);
        invalidCats.add(new JsonAdaptedCategory(INVALID_CAT));
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, invalidCats, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, VALID_REPAID_DATE);
        assertThrows(IllegalValueException.class, log::toModelType);
    }

    @Test
    public void toModelType_invalidIsRepaid_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        INVALID_IS_REPAID, VALID_REPAID_DATE);
        String expectedMessage = "Field 'isValid' is in wrong format, should either be true or false!";
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_nullIsRepaid_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        null, VALID_REPAID_DATE);
        String expectedMessage = "Field 'isValid' is in wrong format, should either be true or false!";
        assertThrows(IllegalValueException.class, expectedMessage, log::toModelType);
    }

    @Test
    public void toModelType_invalidRepaidDate_throwsIllegalValueException() {
        JsonAdaptedBorrowLogEntry log =
                new JsonAdaptedBorrowLogEntry(VALID_AMOUNT, VALID_TDATE, VALID_DESC,
                        VALID_TMET, VALID_CATS, LOG03.getLogEntryType(), VALID_FROM,
                        VALID_IS_REPAID, INVALID_REPAID_DATE);
        String expectedMessage = RepaidDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, log::toModelType);
    }

}
