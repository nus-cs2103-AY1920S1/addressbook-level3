package seedu.savenus.model.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_LEMAK;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class PurchaseTest {
    private final String testTimeOfPurchaseInMillisSinceEpoch = "1570680000000"; // 2019/10/10 12:00:00
    private final TimeOfPurchase testTimeOfPurchase = new TimeOfPurchase(testTimeOfPurchaseInMillisSinceEpoch);
    private final Purchase testPurchase = new Purchase(CARBONARA, testTimeOfPurchase);

    @Test
    public void test_timeOfPurchase() {
        LocalDateTime time = testPurchase.getTimeOfPurchaseInLocalDateTime();
        LocalDateTime accurateTime = testPurchase.getTimeOfPurchase().getTimeOfPurchaseInLocalDateTime();
        assertEquals(time, accurateTime);
    }

    @Test
    public void test_timeAgo() {
        String timeAgo = testPurchase.getTimeAgoString();
        assertEquals(timeAgo, "Thu, 10 Oct");
    }

    @Test
    public void equals() {
        // same values -> returns true
        Purchase testPurchaseCopy = new Purchase(CARBONARA, testTimeOfPurchase);
        assertTrue(testPurchase.equals(testPurchaseCopy));

        // same object -> returns true
        assertTrue(testPurchase.equals(testPurchase));

        // null -> returns false
        assertFalse(testPurchase.equals(null));

        // different type -> returns false
        assertFalse(testPurchase.equals(5));

        // different food -> returns false
        Purchase editedPurchase = new Purchase(NASI_LEMAK, testTimeOfPurchase);
        assertFalse(testPurchase.equals(editedPurchase));

        // different time of purchase -> returns false
        editedPurchase = new Purchase(CARBONARA, TimeOfPurchase.generate());
        assertFalse(testPurchase.equals(editedPurchase));
    }
}
