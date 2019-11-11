package seedu.ichifund.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.storage.JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_BUS;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

public class JsonAdaptedTransactionTest {
    private static final String INVALID_DESCRIPTION = "!!!";
    private static final String INVALID_AMOUNT = "234.299";
    private static final String INVALID_TRANSACTION_TYPE = "income";
    private static final String INVALID_CATEGORY = "@*1238";
    private static final String INVALID_REPEATER_UNIQUE_ID = "sda";

    private static final String VALID_DESCRIPTION = TRANSACTION_BUS.getDescription().toString();
    private static final String VALID_AMOUNT = TRANSACTION_BUS.getAmount().toString();
    private static final String VALID_CATEGORY = TRANSACTION_BUS.getCategory().toString();
    private static final String VALID_TRANSACTION_TYPE = TRANSACTION_BUS.getTransactionType().toString();
    private static final String VALID_REPEATER_UNIQUE_ID = "";
    private static final JsonAdaptedDate VALID_DATE = new JsonAdaptedDate(TRANSACTION_BUS.getDate());


    @Test
    public void toModelType_validTransactionDetails_returnsTransaction() throws Exception {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(TRANSACTION_BUS);
        assertEquals(TRANSACTION_BUS, transaction.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(INVALID_DESCRIPTION, VALID_AMOUNT, VALID_CATEGORY, VALID_DATE,
                        VALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(null, VALID_AMOUNT, VALID_CATEGORY, VALID_DATE,
                        VALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, INVALID_AMOUNT, VALID_CATEGORY, VALID_DATE,
                        VALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, null, VALID_CATEGORY, VALID_DATE,
                        VALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, VALID_AMOUNT, INVALID_CATEGORY, VALID_DATE,
                        VALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = Category.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullCategory_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, VALID_AMOUNT, null, VALID_DATE,
                        VALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidTransactionType_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_CATEGORY, VALID_DATE,
                        INVALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = TransactionType.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullTransactionType_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_CATEGORY, VALID_DATE,
                        null, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TransactionType.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidRepeaterUniqueId_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_CATEGORY, VALID_DATE,
                        VALID_TRANSACTION_TYPE, INVALID_REPEATER_UNIQUE_ID);
        String expectedMessage = RepeaterUniqueId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullRepeaterUniqueId_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_CATEGORY, VALID_DATE,
                        VALID_TRANSACTION_TYPE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RepeaterUniqueId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction =
                new JsonAdaptedTransaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_CATEGORY, null,
                        VALID_TRANSACTION_TYPE, VALID_REPEATER_UNIQUE_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
}
