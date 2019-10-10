package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.PurchaseList;

/**
 * Tests logic of purchase list class.
 */
public class PurchaseListTest {

    private PurchaseList purchaseList;

    @BeforeEach
    public void setUp() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        purchaseList = new PurchaseList(listPurchases);
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {

        assertEquals(new PurchaseStub().getDescription(), purchaseList.getPurchase(1).getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), purchaseList.getPurchase(1).getMoneySpent());
    }

    @Test
    public void addPurchase_normalInput_addedCorrectly() {
        purchaseList.addSinglePurchase(new PurchaseStub());
        assertEquals(new PurchaseStub().getDescription(), purchaseList.getPurchase(1).getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), purchaseList.getPurchase(1).getMoneySpent());
        assertEquals(4, purchaseList.getNumPurchases());
    }

    @Test
    public void countNumPurchases_normalInput_addedCorrectly() {
        assertEquals(3, purchaseList.getNumPurchases());
    }

    @Test
    public void deletePurchase_normalInput_deletedCorrectly() {
        Purchase removedPurchase = purchaseList.deletePurchase(3);
        assertEquals(new PurchaseStub().getDescription(), removedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), removedPurchase.getMoneySpent());
        assertEquals(2, purchaseList.getNumPurchases());
    }

    @Test
    public void deletePurchase_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> purchaseList.deletePurchase(4));
        assertEquals(3, purchaseList.getNumPurchases());
    }

    @Test
    public void totalSpending_normalInput_addedCorrectly() {
        assertEquals(15.0, purchaseList.totalSpending());
    }

    private static class PurchaseStub extends Purchase {
        public PurchaseStub() {
            super("lunch at Saizerya", 5.00);
        }
    }
}
