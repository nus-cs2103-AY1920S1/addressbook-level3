package seedu.address.model.itinerary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidExpense_throwsIllegalArgumentException() {
        String invalidExpense = "";
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidExpense));
    }

    @Test
    public void isValid() {
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // spaces only
        //        assertFalse(Budget.isValidBudget("0.1234")); // containing more than one decimal place
        assertFalse(Budget.isValidBudget("Not a number")); // containing only alphabets

        // valid addresses
        assertTrue(Budget.isValidBudget("0")); // no decimal point
        assertTrue(Location.isValidLocation("1.0")); // one decimal point
        assertTrue(Location.isValidLocation("9.99")); // two decimal points
    }

}
