package thrift.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.storage.JsonAdaptedBudget.MISSING_FIELD_MESSAGE_FORMAT;
import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import thrift.commons.exceptions.IllegalValueException;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetValue;
import thrift.testutil.TypicalTransactions;

public class JsonAdaptedBudgetTest {

    private static final String INVALID_DATE = "AA/BBBB";
    private static final String INVALID_VALUE = ".00";

    private static final String VALID_DATE = "10/2019";
    private static final String VALID_VALUE = TypicalTransactions.OCT_BUDGET.getBudgetValue().toString();

    @Test
    public void toModelType_validBudgetDetails_returnsBudget() throws IllegalValueException {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(TypicalTransactions.OCT_BUDGET);
        assertEquals(TypicalTransactions.OCT_BUDGET.toString(), budget.toModelType().toString());
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(null, VALID_VALUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "month String");
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(INVALID_DATE, VALID_VALUE);
        String expectedMessage = Budget.DATE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_nullValue_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BudgetValue.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

    @Test
    public void toModelType_invalidValue_throwsIllegalValueException() {
        JsonAdaptedBudget budget = new JsonAdaptedBudget(VALID_DATE, INVALID_VALUE);
        String expectedMessage = BudgetValue.VALUE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, budget::toModelType);
    }

}
