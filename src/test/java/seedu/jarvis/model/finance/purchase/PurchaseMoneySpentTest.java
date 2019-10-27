package seedu.jarvis.model.finance.purchase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PurchaseMoneySpentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PurchaseMoneySpent(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new PurchaseMoneySpent(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        //null amount
        assertThrows(NullPointerException.class, () -> PurchaseMoneySpent.isValidAmount(null));

        //invalid amounts
        assertFalse(PurchaseMoneySpent.isValidAmount("")); // empty string
        assertFalse(PurchaseMoneySpent.isValidAmount(" ")); // spaces only
        assertFalse(PurchaseMoneySpent.isValidAmount("9011p041")); // alphabets within digits

        //valid amounts
        assertTrue(PurchaseMoneySpent.isValidAmount("13.50"));
        assertTrue(PurchaseMoneySpent.isValidAmount("13"));
    }

    @Test
    public void isEquals() {
        PurchaseMoneySpent amount1 = new PurchaseMoneySpent("5.0");
        PurchaseMoneySpent amount2 = new PurchaseMoneySpent("5.0");
        assertTrue(amount1.equals(amount2));
    }
}
