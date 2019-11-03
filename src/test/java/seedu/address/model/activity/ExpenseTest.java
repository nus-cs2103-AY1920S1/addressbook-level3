package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

public class ExpenseTest {
    private static Amount amount = new Amount(1);
    private static String description = "Fish fillet";

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Expense(BENSON.getPrimaryKey(), amount, null));
        assertThrows(NullPointerException.class, () -> new Expense(BENSON.getPrimaryKey(), null, description));
    }

    @Test
    public void isDeleted_deleteExpense() {
        Expense expense = new Expense(BENSON.getPrimaryKey(), amount, description);
        assertFalse(expense.isDeleted());
        expense.delete();
        assertTrue(expense.isDeleted());
    }
}
