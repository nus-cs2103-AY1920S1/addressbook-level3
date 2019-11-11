package seedu.address.model.finance.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Budget(null, null, null, null, null));
    }

    @Test
    public void isValidBudgetType() {
        // null budget type
        assertThrows(NullPointerException.class, () -> Budget.isValidBudgetType(null));

        // invalid budget type
        assertFalse(Budget.isValidBudgetType("")); // empty string
        assertFalse(Budget.isValidBudgetType(" ")); // spaces only
        assertFalse(Budget.isValidBudgetType("^")); // only non-alphanumeric characters
        assertFalse(Budget.isValidBudgetType("peter*")); // contains non-alphanumeric characters

        // valid budget type
        assertTrue(Budget.isValidBudgetType("all"));
        assertTrue(Budget.isValidBudgetType("met"));
        assertTrue(Budget.isValidBudgetType("place"));
        assertTrue(Budget.isValidBudgetType("cat"));
    }
}
