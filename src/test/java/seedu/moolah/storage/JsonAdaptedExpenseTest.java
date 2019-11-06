package seedu.moolah.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.storage.JsonAdaptedExpense.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.BUSAN_TRIP;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.expense.UniqueIdentifier;

public class JsonAdaptedExpenseTest {
    private static final String INVALID_DESCRIPTION = "R@chel";
    private static final String INVALID_PRICE = "+651234";
    private static final String INVALID_CATEGORY = "#friend";
    private static final String INVALID_UNIQUE_IDENTIFIER = "Expense1245";
    private static final String INVALID_TIMESTAMP_GENERAL = "blah";
    private static final String INVALID_TIMESTAMP_FUTURE = "tomorrow";
    private static final String INVALID_BUDGET_NAME = "sch@@l";

    private static final String VALID_DESCRIPTION = BUSAN_TRIP.getDescription().toString();
    private static final String VALID_PRICE = BUSAN_TRIP.getPrice().toString();
    private static final String VALID_CATEGORY = BUSAN_TRIP.getCategory().getCategoryName();
    private static final String VALID_UNIQUE_IDENTIFIER = BUSAN_TRIP.getUniqueIdentifier().toString();
    private static final String VALID_TIMESTAMP = BUSAN_TRIP.getTimestamp().toString();
    private static final String VALID_BUDGET_NAME = "school";

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense expense = new JsonAdaptedExpense(BUSAN_TRIP);
        assertEquals(BUSAN_TRIP, expense.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(INVALID_DESCRIPTION, VALID_PRICE, VALID_CATEGORY,
                        VALID_TIMESTAMP, VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(null, VALID_PRICE, VALID_CATEGORY, VALID_TIMESTAMP,
                        VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, INVALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, null,
                        VALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidCategory_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                        INVALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        assertThrows(IllegalValueException.class, expense::toModelType);
    }

    @Test
    public void toModelType_invalidUniqueIdentifier_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME, INVALID_UNIQUE_IDENTIFIER);
        String expectedMessage = UniqueIdentifier.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullUniqueIdentifier_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, VALID_BUDGET_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, UniqueIdentifier.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidTimestamp_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, INVALID_TIMESTAMP_GENERAL, VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_GENERAL;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);

        expense = new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                VALID_CATEGORY, INVALID_TIMESTAMP_FUTURE, VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullTimestamp_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, null, VALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_invalidBudgetName_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, INVALID_BUDGET_NAME, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }

    @Test
    public void toModelType_nullBudgetName_throwsIllegalValueException() {
        JsonAdaptedExpense expense =
                new JsonAdaptedExpense(VALID_DESCRIPTION, VALID_PRICE,
                        VALID_CATEGORY, VALID_TIMESTAMP, null, VALID_UNIQUE_IDENTIFIER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Budget Name");
        assertThrows(IllegalValueException.class, expectedMessage, expense::toModelType);
    }
}
