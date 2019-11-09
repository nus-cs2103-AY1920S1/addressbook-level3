package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBudgets.KOREA;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenseList;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Name;

public class JsonAdaptedBudgetTest {
    private static final String INVALID_NAME = "  ";
    private static final String INVALID_AMOUNT = "+$@654";
    private static final String INVALID_CURRENCY = "X12";
    private static final String INVALID_START_DATE = "Sat";
    private static final String INVALID_END_DATE = "Thursday 1/1/2019";

    private static final String VALID_NAME = KOREA.getName().toString();
    private static final String VALID_AMOUNT = KOREA.getAmount().toString();
    private static final String VALID_CURRENCY = KOREA.getCurrency().toString();
    private static final String VALID_START_DATE = KOREA.getStartDate().rawValue;
    private static final String VALID_END_DATE = KOREA.getEndDate().rawValue;
    private static final JsonSerializableExpenseList VALID_EXPENSELIST =
        new JsonSerializableExpenseList(new ExpenseList());

    @Test
    public void toModelType_validBudgetDetails_returnsBudget() throws Exception {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(KOREA);
        assertEquals(KOREA, budget.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(INVALID_NAME, VALID_AMOUNT, VALID_AMOUNT, VALID_CURRENCY, VALID_START_DATE,
                VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(null, VALID_AMOUNT, VALID_AMOUNT, VALID_CURRENCY, VALID_START_DATE,
                VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_NAME, INVALID_AMOUNT, VALID_AMOUNT ,VALID_CURRENCY, VALID_START_DATE,
                VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_NAME, null, VALID_AMOUNT ,VALID_CURRENCY, VALID_START_DATE,
                VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidCurrency_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_NAME, VALID_AMOUNT, VALID_AMOUNT, INVALID_CURRENCY, VALID_START_DATE,
                VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = Currency.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullCurrency_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_NAME, VALID_AMOUNT, VALID_AMOUNT, null, VALID_START_DATE,
                VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Currency.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_NAME, VALID_AMOUNT, VALID_AMOUNT, VALID_CURRENCY, INVALID_START_DATE,
                    VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_NAME, VALID_AMOUNT, VALID_AMOUNT, VALID_CURRENCY, null,
                VALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
            new JsonAdaptedBudget(VALID_NAME, VALID_AMOUNT, VALID_AMOUNT, VALID_CURRENCY, VALID_START_DATE,
                INVALID_END_DATE, VALID_EXPENSELIST);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
            new JsonAdaptedBudget(VALID_NAME, VALID_AMOUNT, VALID_AMOUNT, VALID_CURRENCY, VALID_START_DATE,
            null, VALID_EXPENSELIST);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

}
