package seedu.moneygowhere.model.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudgetValue_throwsIllegalArgumentException() {
        String invalidBudget = "";
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        //invalid Budgets
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // spaces only
        assertFalse(Budget.isValidBudget("incorrect")); // strings need to be numbers
        assertFalse(Budget.isValidBudget("-1")); //  numeric strings need to be positive
        assertFalse(Budget.isValidBudget(-1)); //  cannot be negative

        //valid Budget
        assertTrue(Budget.isValidBudget("0"));
        assertTrue(Budget.isValidBudget("0.123"));
        assertTrue(Budget.isValidBudget("10000"));
        assertTrue(Budget.isValidBudget(0));
        assertTrue(Budget.isValidBudget(0.123));
        assertTrue(Budget.isValidBudget(10000));
    }
}
