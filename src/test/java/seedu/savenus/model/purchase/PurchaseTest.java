package seedu.savenus.model.purchase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.TypicalMenu.NASI_LEMAK;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;

public class PurchaseTest {
    private final String testTimeOfPurchaseInMillisSinceEpoch = "1570680000000"; // 2019/10/10 12:00:00
    private final TimeOfPurchase testTimeOfPurchase = new TimeOfPurchase(testTimeOfPurchaseInMillisSinceEpoch);
    private final Purchase testPurchase = new Purchase(new Name("testFoodName"), new Price("1.50"), testTimeOfPurchase);

    @Test
    public void equals() {
        // same values -> returns true
        Purchase testPurchaseCopy = new Purchase(new Name("testFoodName"), new Price("1.50"), testTimeOfPurchase);
        assertTrue(testPurchase.equals(testPurchaseCopy));

        // same object -> returns true
        assertTrue(testPurchase.equals(testPurchase));

        // null -> returns false
        assertFalse(testPurchase.equals(null));

        // different type -> returns false
        assertFalse(testPurchase.equals(5));

        // different food -> returns false
        assertFalse(testPurchase.equals(NASI_LEMAK));

        // different name -> returns false
        Purchase editedPurchase = new Purchase(new Name("fakeTestFoodName"), new Price("1.50"), testTimeOfPurchase);
        assertFalse(testPurchase.equals(editedPurchase));

        // different price -> returns false
        editedPurchase = new Purchase(new Name("testFoodName"), new Price("2.50"), testTimeOfPurchase);
        assertFalse(testPurchase.equals(editedPurchase));

        // different time of purchase -> returns false
        editedPurchase = new Purchase(new Name("testFoodName"), new Price("1.50"), TimeOfPurchase.generate());
        assertFalse(testPurchase.equals(editedPurchase));
    }
}
