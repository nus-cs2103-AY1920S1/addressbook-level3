package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.PurchaseList;

/**
 * Tests logic of purchase list class.
 */
public class PurchaseListTest {

    @Test
    public static void getPurchase_normalInput_retrievedCorrectly() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        PurchaseList p = new PurchaseList(listPurchases);
        assertEquals(new PurchaseStub().getDescription(), p.getPurchase(1).getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), p.getPurchase(1).getMoneySpent());
    }

    @Test
    public static void addPurchase_normalInput_addedCorrectly() {
        PurchaseList p = new PurchaseList(new ArrayList<>());
        p.addSinglePurchase(new PurchaseStub());
        assertEquals(new PurchaseStub().getDescription(), p.getPurchase(1).getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), p.getPurchase(1).getMoneySpent());
        assertEquals(1, p.getNumPurchases());
    }

    @Test
    public static void countNumPurchases_normalInput_addedCorrectly() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        PurchaseList p = new PurchaseList(listPurchases);
        assertEquals(3, p.getNumPurchases());
    }

    @Test
    public static void deletePurchase_normalInput_deletedCorrectly() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        PurchaseList p = new PurchaseList(listPurchases);
        Purchase removedPurchase = p.deletePurchase(3);
        assertEquals(new PurchaseStub().getDescription(), removedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), removedPurchase.getMoneySpent());
        assertEquals(2, p.getNumPurchases());
    }

    @Test
    public static void deletePurchase_indexNonexistent_throwsError() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        PurchaseList p = new PurchaseList(listPurchases);
        assertThrows(IndexOutOfBoundsException.class, () -> p.deletePurchase(4));
        assertEquals(3, p.getNumPurchases());
    }

    @Test
    public static void totalSpending_normalInput_addedCorrectly() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        PurchaseList p = new PurchaseList(listPurchases);
        assertEquals(30.0, p.totalSpending());
    }
}

class PurchaseStub extends Purchase {
    public PurchaseStub() {
        super("test purchase", 5.00);
    }
}
