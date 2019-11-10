package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedExpense.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.FOOD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Name;

public class JsonAdaptedExpenseTest {
    private static final String INVALID_NAME = "  ";
    private static final String INVALID_AMOUNT = "+$@654";
    private static final JsonAdaptedCurrency INVALID_CURRENCY = new JsonAdaptedCurrency("X12", 1);
    private static final String INVALID_DATE = "Sat";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = FOOD.getName().toString();
    private static final String VALID_AMOUNT = FOOD.getAmount().toString();

    private static final JsonAdaptedCurrency VALID_CURRENCY = new JsonAdaptedCurrency(FOOD.getCurrency().name, 1);
    private static final String VALID_DATE = FOOD.getDate().toString();
    private static final String VALID_TAG = FOOD.getTag().tagName;

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(FOOD);
        assertEquals(FOOD, expense.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
            new JsonAdaptedExpense(INVALID_NAME, VALID_AMOUNT, VALID_CURRENCY, VALID_DATE, VALID_TAG);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(null, VALID_AMOUNT, VALID_CURRENCY, VALID_DATE, VALID_TAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
            new JsonAdaptedExpense(VALID_NAME, INVALID_AMOUNT, VALID_CURRENCY, VALID_DATE, VALID_TAG);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, null, VALID_CURRENCY, VALID_DATE, VALID_TAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidCurrency_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
            new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, INVALID_CURRENCY, VALID_DATE, VALID_TAG);
        String expectedMessage = Currency.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullCurrency_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, null, VALID_DATE, VALID_TAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Currency.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
            new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, VALID_CURRENCY, INVALID_DATE, VALID_TAG);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, VALID_CURRENCY, null, VALID_TAG);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        String invalidTag = "-";
        JsonAdaptedExpense expense =
            new JsonAdaptedExpense(VALID_NAME, VALID_AMOUNT, VALID_CURRENCY, VALID_DATE, invalidTag);
        assertThrows(IllegalValueException.class, expense::toModelType);
    }

}
