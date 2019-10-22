package seedu.savenus.model.purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.BEE_HOON;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import seedu.savenus.model.purchase.exceptions.PurchaseNotFoundException;

public class PurchaseHistoryListTest {
    private final Purchase testPurchase = new Purchase(CARBONARA);
    private final PurchaseHistoryList testPurchaseHistoryList = new PurchaseHistoryList();
    private final List<Purchase> testListOfPurchases = new ArrayList<>(List.of(
            new Purchase(CARBONARA, new TimeOfPurchase("1570976664361")),
            new Purchase(BEE_HOON, new TimeOfPurchase("1570976665687"))
    ));

    @Test
    public void add_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testPurchaseHistoryList.add(null));
    }

    @Test
    public void remove_nullPurchase_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testPurchaseHistoryList.remove(null));
    }

    @Test
    public void remove_purchaseDoesNotExist_throwsPurchaseNotFoundException() {
        assertThrows(PurchaseNotFoundException.class, () -> testPurchaseHistoryList.remove(testPurchase));
    }

    @Test
    public void remove_existingfood_removesfood() {
        testPurchaseHistoryList.add(testPurchase);
        testPurchaseHistoryList.remove(testPurchase);
        PurchaseHistoryList expectedPurchaseHistoryList = new PurchaseHistoryList();
        assertEquals(expectedPurchaseHistoryList, testPurchaseHistoryList);
    }

    @Test
    public void setPurchases_nullPurchaseHistory_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () -> testPurchaseHistoryList.setPurchases((PurchaseHistoryList) null));
    }

    @Test
    public void setPurchases_purchaseHistory_replacesOwnListWithProvidedPurchaseHistory() {
        testPurchaseHistoryList.add(testPurchase);
        PurchaseHistoryList expectedPurchaseHistoryList = new PurchaseHistoryList();
        expectedPurchaseHistoryList.add(testPurchase);
        testPurchaseHistoryList.setPurchases(expectedPurchaseHistoryList);
        assertEquals(expectedPurchaseHistoryList, testPurchaseHistoryList);
    }

    @Test
    public void setPurchases_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> testPurchaseHistoryList.setPurchases((List<Purchase>) null));
    }

    @Test
    public void setPurchases_list_replacesOwnListWithProvidedList() {
        List<Purchase> purchaseList = Collections.singletonList(testPurchase);
        testPurchaseHistoryList.setPurchases(purchaseList);
        PurchaseHistoryList expectedPurchaseHistoryList = new PurchaseHistoryList();
        expectedPurchaseHistoryList.add(testPurchase);
        assertEquals(expectedPurchaseHistoryList, testPurchaseHistoryList);
    }


    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> testPurchaseHistoryList
                .asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void get_iterator_test() {
        System.out.println(testPurchaseHistoryList.iterator());
        assertNotEquals(testPurchaseHistoryList.iterator(), FXCollections.observableArrayList().iterator());
    }

    @Test
    public void toString_test() {
        assertEquals(testPurchaseHistoryList.toString(), "No Purchases Made");

        testPurchaseHistoryList.add(testPurchase);
        assertEquals(testPurchaseHistoryList.toString(), "Current Purchases: \n"
                + testPurchase.toString());
    }
}
