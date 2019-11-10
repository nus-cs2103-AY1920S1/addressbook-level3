package seedu.address.storage;

import static seedu.address.model.ModelTestUtil.VALID_AMOUNT_EXPENSE_1;
import static seedu.address.model.ModelTestUtil.VALID_DAY_NUMBER_EXPENSE_1;
import static seedu.address.model.ModelTestUtil.VALID_NAME_EXPENSE_1;
import static seedu.address.model.ModelTestUtil.VALID_TYPE_EXPENSE_1;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;

class JsonAdaptedExpenseTest {
    //Blank Name
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws ParseException {
        JsonAdaptedExpense jsonAdaptedExpense = new JsonAdaptedExpense("",
                Double.parseDouble(VALID_AMOUNT_EXPENSE_1),
                VALID_DAY_NUMBER_EXPENSE_1,
                VALID_TYPE_EXPENSE_1);
        String expectedMessage = seedu.address.model.itinerary.Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedExpense::toModelType);
    }

    @Test
    public void toModelType_invalidBudget_throwsIllegalValueException() throws ParseException {
        JsonAdaptedExpense jsonAdaptedExpense = new JsonAdaptedExpense(VALID_NAME_EXPENSE_1,
                -12.49,
                VALID_DAY_NUMBER_EXPENSE_1,
                VALID_TYPE_EXPENSE_1
        );
        String expectedMessage = seedu.address.model.itinerary.Budget.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedExpense::toModelType);
    }

    @Test
    public void toModelType_invalidDayNumber_throwsIllegalValueException() throws ParseException {
        JsonAdaptedExpense jsonAdaptedExpense = new JsonAdaptedExpense(VALID_NAME_EXPENSE_1,
                Double.parseDouble(VALID_AMOUNT_EXPENSE_1),
                "-12",
                VALID_TYPE_EXPENSE_1
        );
        String expectedMessage = seedu.address.model.expense.DayNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedExpense::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() throws ParseException {
        JsonAdaptedExpense jsonAdaptedExpense = new JsonAdaptedExpense(VALID_NAME_EXPENSE_1,
                Double.parseDouble(VALID_AMOUNT_EXPENSE_1),
                VALID_DAY_NUMBER_EXPENSE_1,
                "other"
        );
        String expectedMessage = "Invalid expense type";
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedExpense::toModelType);
    }

}
