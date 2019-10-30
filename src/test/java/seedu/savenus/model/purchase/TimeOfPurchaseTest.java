package seedu.savenus.model.purchase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeOfPurchaseTest {

    @Test
    public void isValidTimeStampTest() {
        // null name
        assertThrows(NullPointerException.class, () -> TimeOfPurchase.isValidTimeStamp(null));

        // invalid remainingBudget
        assertFalse(TimeOfPurchase.isValidTimeStamp("^")); // only non-alphanumeric characters
        assertFalse(TimeOfPurchase.isValidTimeStamp("prata*")); // contains non-alphanumeric characters
        assertFalse(TimeOfPurchase.isValidTimeStamp("-123.50")); // negative remainingBudget
        assertFalse(TimeOfPurchase.isValidTimeStamp("")); // empty string
        assertFalse(TimeOfPurchase.isValidTimeStamp(" ")); // spaces only
        assertFalse(TimeOfPurchase.isValidTimeStamp("           ")); // tons of spaces

        // valid remainingBudget
        assertTrue(TimeOfPurchase.isValidTimeStamp("1570680000000")); // valid remainingBudget
    }

}
