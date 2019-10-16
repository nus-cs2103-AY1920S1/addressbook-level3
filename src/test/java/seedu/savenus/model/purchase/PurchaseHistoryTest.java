package seedu.savenus.model.purchase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.purchase.exceptions.PurchaseNotFoundException;

public class PurchaseHistoryTest {

    private PurchaseHistory purchaseHistory = new PurchaseHistory();
    private Purchase purchase = new Purchase(new Name("anyhow"),
            new Price("1.20"), new TimeOfPurchase("2390"));

    @Test
    public void remove_throwPurchaseNotFoundException() {
        assertThrows(PurchaseNotFoundException.class, () -> purchaseHistory.remove(purchase));
    }

    @Test
    public void setPurchases_successfulRun() {
        assertDoesNotThrow(() -> purchaseHistory.setPurchases(purchaseHistory));
    }

    @Test
    public void toString_correctReturnType() {
        assertEquals(true,
                purchaseHistory.toString() instanceof String);
    }
}
