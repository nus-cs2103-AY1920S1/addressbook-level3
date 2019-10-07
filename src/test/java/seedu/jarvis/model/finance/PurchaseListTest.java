package seedu.jarvis.model.finance;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.PurchaseList;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

/**
 * Tests totalSpending() method //todo edit documentation
 */
public class PurchaseListTest {

    public static void main(String[] args) {
        addPurchase_normalInput_addedCorrectly();
        deletePurchase_normalInput_deletedCorrectly();
        deletePurchase_indexNonexistent_throwsError();
        totalSpending_normalInput_addedCorrectly();
    }

    @Test
    public static void addPurchase_normalInput_addedCorrectly() {
        PurchaseList p = new PurchaseList(new ArrayList<Purchase>());
        p.addSinglePurchase(new PurchaseStub());
        assertEquals(new PurchaseStub().getDescription(), p.getPurchase(1).getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), p.getPurchase(1).getMoneySpent());
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
    }

    @Test
    public static void deletePurchase_indexNonexistent_throwsError() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        PurchaseList p = new PurchaseList(listPurchases);
        assertThrows(IndexOutOfBoundsException.class, () -> p.deletePurchase(4));
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
