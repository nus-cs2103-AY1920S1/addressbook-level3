package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ModelTestUtil.VALID_AMOUNT_EXPENSE_2;
import static seedu.address.model.ModelTestUtil.VALID_DAY_NUMBER_EXPENSE_2;
import static seedu.address.model.ModelTestUtil.VALID_EXPENSE_1;
import static seedu.address.model.ModelTestUtil.VALID_EXPENSE_2;
import static seedu.address.model.ModelTestUtil.VALID_NAME_EXPENSE_2;
import static seedu.address.model.ModelTestUtil.VALID_TYPE_EXPENSE_2;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseTest {

    @Test
    public void isSameExpense() {
        // same object -> returns true
        assertTrue(VALID_EXPENSE_1.isSameExpense(VALID_EXPENSE_1));

        // null -> returns false
        assertFalse(VALID_EXPENSE_1.isSameExpense(null));

        // different name -> returns false
        Expense editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1)
                .setName(new Name(VALID_NAME_EXPENSE_2)).build();
        assertFalse(VALID_EXPENSE_1.isSameExpense(editedExpenseA));

        // same name, same day number, different budget -> returns true
        editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1).setBudget(new Budget(VALID_AMOUNT_EXPENSE_2))
                .build();
        assertTrue(VALID_EXPENSE_1.isSameExpense(editedExpenseA));

        // same name, same budget, same day number -> returns true
        editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1).build();
        assertTrue(VALID_EXPENSE_1.isSameExpense(editedExpenseA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense expenseACopy = ExpenseBuilder.of(VALID_EXPENSE_1).build();
        assertTrue(VALID_EXPENSE_1.equals(expenseACopy));

        // same object -> returns true
        assertTrue(VALID_EXPENSE_1.equals(VALID_EXPENSE_1));

        // null -> returns false
        assertFalse(VALID_EXPENSE_1.equals(null));

        // different type -> returns false
        assertFalse(VALID_EXPENSE_1.equals(5));

        // different expense -> returns false
        assertFalse(VALID_EXPENSE_1.equals(VALID_EXPENSE_2));

        // different type -> returns false
        Expense editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1)
                .setType(VALID_TYPE_EXPENSE_2).build();
        assertFalse(VALID_EXPENSE_1.equals(editedExpenseA));

        // different name -> returns false
        editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1)
                .setName(new Name(VALID_NAME_EXPENSE_2)).build();
        assertFalse(VALID_EXPENSE_1.equals(editedExpenseA));

        // different day number -> returns false
        editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1)
                .setDayNumber(new DayNumber(VALID_DAY_NUMBER_EXPENSE_2)).build();
        assertFalse(VALID_EXPENSE_1.equals(editedExpenseA));

        // different budget -> returns false
        editedExpenseA = ExpenseBuilder.of(VALID_EXPENSE_1)
                .setBudget(new Budget(VALID_AMOUNT_EXPENSE_2)).build();
        assertFalse(VALID_EXPENSE_1.equals(editedExpenseA));

    }

}
