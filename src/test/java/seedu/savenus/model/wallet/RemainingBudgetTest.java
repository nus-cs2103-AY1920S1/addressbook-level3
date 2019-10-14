package seedu.savenus.model.wallet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

public class RemainingBudgetTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemainingBudget(null));
    }

    @Test
    public void constructor_invalidRemainingBudget_throwsIllegalArgumentException() {
        String invalidRemainingBudget = "abc";
        assertThrows(IllegalArgumentException.class, () -> new RemainingBudget(invalidRemainingBudget));
    }

    @Test
    public void constructor_remainingBudgetWithSpacesOnly_throwsIllegalArgumentException() {
        String invalidRemainingBudget = "                      ";
        assertThrows(IllegalArgumentException.class, () -> new  RemainingBudget(invalidRemainingBudget));
    }

    @Test
    public void isValidRemainingBudgetTest() {
        // null name
        assertThrows(NullPointerException.class, () -> RemainingBudget.isValidRemainingBudget(null));

        // invalid remainingBudget
        assertFalse(RemainingBudget.isValidRemainingBudget("^")); // only non-alphanumeric characters
        assertFalse(RemainingBudget.isValidRemainingBudget("prata*")); // contains non-alphanumeric characters
        // assertFalse(RemainingBudget.isValidRemainingBudget("123400000000000000")); // too long remainingBudget
        assertFalse(RemainingBudget.isValidRemainingBudget("-123.50")); // negative remainingBudget

        // valid remainingBudget
        assertTrue(RemainingBudget.isValidRemainingBudget("123.50")); // valid remainingBudget
    }

    @Test
    public void isEmptyRemainingBudget() {
        assertFalse(RemainingBudget.isValidRemainingBudget("")); // empty string
        assertFalse(RemainingBudget.isValidRemainingBudget(" ")); // spaces only
        assertFalse(RemainingBudget.isValidRemainingBudget("           ")); // tons of spaces
    }

}
