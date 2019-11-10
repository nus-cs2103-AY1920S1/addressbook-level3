package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AFRICA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_BUDGET_AFRICA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_BUDGET_BALI;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseTest {

    public static final Expense EXPENSE_A = ExpenseBuilder.newInstance().setName(new Name("Expense A"))
            .setBudget(new Budget(123))
            .setDayNumber(new DayNumber("1"))
            .setType("misc")
            .build();
    public static final Expense EXPENSE_B = ExpenseBuilder.newInstance().setName(new Name("Expense B"))
            .setBudget(new Budget(123.12))
            .setDayNumber(new DayNumber("2"))
            .setType("planned")
            .build();

    @Test
    public void isSameExpense() {
        // same object -> returns true
        assertTrue(EXPENSE_A.isSameExpense(EXPENSE_A));

        // null -> returns false
        assertFalse(EXPENSE_A.isSameExpense(null));

        // different name -> returns false
        Expense editedExpenseA = ExpenseBuilder.of(EXPENSE_A)
                .setName(new Name(VALID_NAME_AFRICA)).build();
        assertFalse(EXPENSE_A.isSameExpense(editedExpenseA));

        // same name, same day number, different budget -> returns true
        editedExpenseA = ExpenseBuilder.of(EXPENSE_A).setBudget(new Budget(VALID_TOTAL_BUDGET_AFRICA))
                .build();
        assertTrue(EXPENSE_A.isSameExpense(editedExpenseA));

        // same name, same budget, same day number -> returns true
        editedExpenseA = ExpenseBuilder.of(EXPENSE_A).build();
        assertTrue(EXPENSE_A.isSameExpense(editedExpenseA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense expenseACopy = ExpenseBuilder.of(EXPENSE_A).build();
        assertTrue(EXPENSE_A.equals(expenseACopy));

        // same object -> returns true
        assertTrue(EXPENSE_A.equals(EXPENSE_A));

        // null -> returns false
        assertFalse(EXPENSE_A.equals(null));

        // different type -> returns false
        assertFalse(EXPENSE_A.equals(5));

        // different expense -> returns false
        assertFalse(EXPENSE_A.equals(EXPENSE_B));

        // different name -> returns false
        Expense editedExpenseA = ExpenseBuilder.of(EXPENSE_A)
                .setName(new Name(VALID_NAME_BALI)).build();
        assertFalse(EXPENSE_A.equals(editedExpenseA));

        // different day number -> returns false
        editedExpenseA = ExpenseBuilder.of(EXPENSE_A).setDayNumber(new DayNumber("2")).build();
        assertFalse(EXPENSE_A.equals(editedExpenseA));

        // different budget -> returns false
        editedExpenseA = ExpenseBuilder.of(EXPENSE_A)
                .setBudget(new Budget(VALID_TOTAL_BUDGET_BALI)).build();
        assertFalse(EXPENSE_A.equals(editedExpenseA));

    }

}
