package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;

public class JsonAdaptedExpenseTest {
    private static Expense expense = new Expense(BENSON, new Amount(1.5));

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense jsonAdaptedExpense = new JsonAdaptedExpense(expense);
        assertEquals(expense, jsonAdaptedExpense.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpense jsonAdaptedExpense = new JsonAdaptedExpense(new JsonAdaptedPerson(BENSON), -0.1);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedExpense::toModelType);
    }
}
