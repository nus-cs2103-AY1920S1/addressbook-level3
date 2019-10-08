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

    private PurchaseList p;

    @BeforeEach
    public void setUp() {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        p = new PurchaseList(listPurchases);
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {

        assertEquals(new PurchaseStub().getDescription(), p.getPurchase(1).getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), p.getPurchase(1).getMoneySpent());
    }

    @Test
    public void addPurchase_normalInput_addedCorrectly() {
        p.addSinglePurchase(new PurchaseStub());
        assertEquals(new PurchaseStub().getDescription(), p.getPurchase(1).getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), p.getPurchase(1).getMoneySpent());
        assertEquals(4, p.getNumPurchases());
    }

    @Test
    public void countNumPurchases_normalInput_addedCorrectly() {
        assertEquals(3, p.getNumPurchases());
    }

    @Test
    public void deletePurchase_normalInput_deletedCorrectly() {
        Purchase removedPurchase = p.deletePurchase(3);
        assertEquals(new PurchaseStub().getDescription(), removedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), removedPurchase.getMoneySpent());
        assertEquals(2, p.getNumPurchases());
    }

    @Test
    public void deletePurchase_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> p.deletePurchase(4));
        assertEquals(3, p.getNumPurchases());
    }

    @Test
    public void totalSpending_normalInput_addedCorrectly() {
        assertEquals(15.0, p.totalSpending());
    }
}

class PurchaseStub extends Purchase {
    public PurchaseStub() {
        super("lunch at Saizerya", 5.00);
    }
}
