package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBudgets.SCHOOL;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.budget.Percentage;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;

public class JsonAdaptedBudgetTest {

    public static final String VALID_DESCRIPTION = "school related expenses";
    public static final String VALID_AMOUNT = "300";
    public static final String VALID_START_DATE = "01-10-2019";
    public static final String VALID_END_DATE = "01-11-2019";
    public static final String VALID_PERIOD = "month";
    public static final List<JsonAdaptedExpense> VALID_EXPENSES = new ArrayList<>();
    public static final boolean VALID_IS_PRIMARY = true;
    public static final String VALID_PROPORTION_USED = "60%";

    private static final String INVALID_DESCRIPTION = "sch@@l related expenses";
    private static final String INVALID_AMOUNT = "-200";
    private static final String INVALID_START_DATE = "2019-10-01";
    private static final String INVALID_END_DATE = "2019-11-01";
    private static final String INVALID_PERIOD = "dfa";
    private static final String INVALID_PROPORTION_USED = "125%";

    @Test
    public void toModelType_validBudgetDetails_returnsBudget() throws Exception {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(SCHOOL);
        assertEquals(SCHOOL, budget.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(INVALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(null, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, INVALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, null, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, INVALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_DATE;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, null, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, INVALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_DATE;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, null,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidPeriod_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        INVALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_PERIOD;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullPeriod_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        null, VALID_EXPENSES, VALID_IS_PRIMARY, VALID_PROPORTION_USED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Period.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidProportionUsed_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, INVALID_PROPORTION_USED);
        String expectedMessage = Percentage.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullProportionUsed_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSES, VALID_IS_PRIMARY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Percentage.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }
}
