package seedu.savenus.model.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.purchase.exceptions.PurchaseNotFoundException;

public class PurchaseHistoryTest {
    private final Purchase testPurchase = new Purchase(new Name("testFoodName"), new Price("1.50"));
    private final PurchaseHistory testPurchaseHistory = new PurchaseHistory();
    private final List<Purchase> testListOfPurchases = new ArrayList<>(List.of(
            new Purchase(new Name("Ji Fan"), new Price("3.99"), new TimeOfPurchase("1570976664361")),
            new Purchase(new Name("Wagyu steak"), new Price("50.00"), new TimeOfPurchase("1570976665687"))
    ));

    @Test
    public void add_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testPurchaseHistory.add(null));
    }


    @Test
    public void remove_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testPurchaseHistory.remove(null));
    }

    @Test
    public void remove_purchaseDoesNotExist_throwsPurchaseNotFoundException() {
        assertThrows(PurchaseNotFoundException.class, () -> testPurchaseHistory.remove(testPurchase));
    }

    @Test
    public void remove_existingfood_removesfood() {
        testPurchaseHistory.add(testPurchase);
        testPurchaseHistory.remove(testPurchase);
        PurchaseHistory expectedPurchaseHistory = new PurchaseHistory();
        assertEquals(expectedPurchaseHistory, testPurchaseHistory);
    }

    @Test
    public void setPurchases_nullPurchaseHistory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testPurchaseHistory.setPurchases((PurchaseHistory) null));
    }

    @Test
    public void setPurchases_purchaseHistory_replacesOwnListWithProvidedPurchaseHistory() {
        testPurchaseHistory.add(testPurchase);
        PurchaseHistory expectedPurchaseHistory = new PurchaseHistory();
        expectedPurchaseHistory.add(testPurchase);
        testPurchaseHistory.setPurchases(expectedPurchaseHistory);
        assertEquals(expectedPurchaseHistory, testPurchaseHistory);
    }

    @Test
    public void setPurchases_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testPurchaseHistory.setPurchases((List<Purchase>) null));
    }

    @Test
    public void setPurchases_list_replacesOwnListWithProvidedList() {
        List<Purchase> purchaseList = Collections.singletonList(testPurchase);
        testPurchaseHistory.setPurchases(purchaseList);
        PurchaseHistory expectedPurchaseHistory = new PurchaseHistory();
        expectedPurchaseHistory.add(testPurchase);
        assertEquals(expectedPurchaseHistory, testPurchaseHistory);
    }


    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> testPurchaseHistory
                .asUnmodifiableObservableList().remove(0));
    }
}
