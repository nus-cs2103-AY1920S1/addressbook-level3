package seedu.moolah.storage;

import static seedu.moolah.storage.JsonAdaptedBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moolah.commons.exceptions.IllegalValueException;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;

public class JsonAdaptedBudgetTest {

    public static final String VALID_DESCRIPTION = "school related expenses";
    public static final String VALID_AMOUNT = "300";
    public static final String VALID_START_DATE = "01-10-2019";
    public static final String VALID_END_DATE = "01-11-2019";
    public static final String VALID_PERIOD = "month";
    public static final List<JsonAdaptedExpense> VALID_EXPENSES = new ArrayList<>();
    public static final List<String> VALID_EXPENSE_IDS = new ArrayList<>();
    public static final boolean VALID_IS_PRIMARY = true;
    public static final String VALID_PROPORTION_USED = "60%";

    private static final String INVALID_DESCRIPTION = "sch@@l related expenses";
    private static final String INVALID_AMOUNT = "-200";
    private static final String INVALID_START_DATE = "blah";
    private static final String INVALID_END_DATE = "bleh";
    private static final String INVALID_PERIOD = "dfa";
    private static final String INVALID_PROPORTION_USED = "-2%";

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(INVALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(null, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, INVALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, null, VALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, INVALID_START_DATE, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_GENERAL;
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, null, VALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, INVALID_END_DATE,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_GENERAL;
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, null,
                        VALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Timestamp.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_invalidPeriod_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        INVALID_PERIOD, VALID_EXPENSE_IDS);
        String expectedMessage = Timestamp.MESSAGE_CONSTRAINTS_PERIOD;
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }

    @Test
    public void toModelType_nullPeriod_throwsIllegalValueException() {
        JsonAdaptedBudget budget =
                new JsonAdaptedBudget(VALID_DESCRIPTION, VALID_AMOUNT, VALID_START_DATE, VALID_END_DATE,
                        null, VALID_EXPENSE_IDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Period.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> budget.toModelType(VALID_EXPENSES));
    }
}
