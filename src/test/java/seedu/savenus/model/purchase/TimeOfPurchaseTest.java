package seedu.savenus.model.purchase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeOfPurchaseTest {

    @Test
    public void isValidTimeOfPurchaseTest() {
        // null name
        assertThrows(NullPointerException.class, () -> TimeOfPurchase.isValidTimeOfPurchase(null));

        // invalid remainingBudget
        assertFalse(TimeOfPurchase.isValidTimeOfPurchase("^")); // only non-alphanumeric characters
        assertFalse(TimeOfPurchase.isValidTimeOfPurchase("prata*")); // contains non-alphanumeric characters
        assertFalse(TimeOfPurchase.isValidTimeOfPurchase("-123.50")); // negative remainingBudget
        assertFalse(TimeOfPurchase.isValidTimeOfPurchase("")); // empty string
        assertFalse(TimeOfPurchase.isValidTimeOfPurchase(" ")); // spaces only
        assertFalse(TimeOfPurchase.isValidTimeOfPurchase("           ")); // tons of spaces

        // valid remainingBudget
        assertTrue(TimeOfPurchase.isValidTimeOfPurchase("1570680000000")); // valid remainingBudget
    }

}
