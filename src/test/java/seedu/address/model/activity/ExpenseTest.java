package seedu.address.model.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ExpenseTest {
    private static Person person = new PersonBuilder().build();
    private static Amount amount = new Amount(1);

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Expense(person, null));
        assertThrows(NullPointerException.class, () -> new Expense(null, amount));
    }

    @Test
    public void isDeleted_deleteExpense() {
        Expense expense = new Expense(person, amount);
        assertFalse(expense.isDeleted());
        expense.delete();
        assertTrue(expense.isDeleted());
    }
}
