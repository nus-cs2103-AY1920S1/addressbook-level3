package seedu.moneygowhere.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.storage.JsonAdaptedBudget.INVALID_FIELD_MESSAGE_FORMAT;
import static seedu.moneygowhere.storage.JsonAdaptedBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.budget.BudgetMonth;

class JsonAdaptedBudgetTest {

    private static final Budget VALID_BUDGET = new Budget(100, "10/2019");
    private static final String INVALID_MONTH = "2019/99999";
    private static final String INVALID_VALUE = "asdf";

    private static final String VALID_VALUE = "100";
    private static final String VALID_MONTH = "10/2019";

    @Test
    public void toModelType_validInput_success() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_VALUE, VALID_MONTH);
        try {
            assertEquals(VALID_BUDGET, budget.toModelType());
        } catch (IllegalValueException er) {
            assertEquals(VALID_BUDGET, "invalid Budget.toModelType()");
        }

    }

    @Test
    public void toModelType_invalidMonth_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_VALUE, INVALID_MONTH);
        assertThrows(IllegalValueException.class, BudgetMonth.MESSAGE_CONSTRAINTS, budget::toModelType);
    }

    @Test
    public void toModelType_nullMonth_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_VALUE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "BudgetMonth");
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(INVALID_VALUE, VALID_MONTH);
        String expectedMessage = String.format(INVALID_FIELD_MESSAGE_FORMAT, "value");
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullValue_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(null, VALID_MONTH);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }
}
