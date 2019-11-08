package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Amount;
import seedu.address.model.activity.Expense;

public class JsonAdaptedExpenseTest {
    private static Expense expense = new Expense(BENSON.getPrimaryKey(), new Amount(1.5), "Fish fillet");
    private static Expense expense2 =
            new Expense(BENSON.getPrimaryKey(), new Amount(1.5), "Fish fillet", ALICE.getPrimaryKey());

    @Test
    public void toModelType_validExpenseDetails_returnsExpense() throws Exception {
        JsonAdaptedExpense jsonAdaptedExpense = new JsonAdaptedExpense(expense);
        assertEquals(expense, jsonAdaptedExpense.toModelType());

        jsonAdaptedExpense = new JsonAdaptedExpense(expense2);
        assertEquals(expense2, jsonAdaptedExpense.toModelType());
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedExpense jsonAdaptedExpense =
                new JsonAdaptedExpense(BENSON.getPrimaryKey(), -0.1, "", false, false);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedExpense::toModelType);
    }
}
