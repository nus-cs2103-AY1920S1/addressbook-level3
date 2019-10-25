package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.jarvis.model.financetracker.PurchaseList;
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.financetracker.purchase.PurchaseDescription;
import seedu.jarvis.model.financetracker.purchase.PurchaseMoneySpent;
import seedu.jarvis.testutil.finance.PurchaseBuilder;

/**
 * Tests logic of purchase list class.
 */
public class PurchaseListTest {

    private PurchaseList purchaseList;

    @BeforeEach
    public void setUp() {
        ObservableList<Purchase> listPurchases = FXCollections.observableArrayList();
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        listPurchases.add(new PurchaseStub());
        purchaseList = new PurchaseList(listPurchases);
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {
        assertEquals(new PurchaseStub().getDescription(), purchaseList.getPurchase(1)
                .getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), purchaseList.getPurchase(1)
                .getMoneySpent());
        assertEquals(new PurchaseStub().getDateOfPurchase(), purchaseList.getPurchase(1)
                .getDateOfPurchase());
    }

    @Test
    public void addPurchase_normalInput_addedCorrectly() {
        purchaseList.addSinglePurchase(new PurchaseStub());
        assertEquals(new PurchaseStub().getDescription(), purchaseList.getPurchase(4)
                .getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), purchaseList.getPurchase(4)
                .getMoneySpent());
        assertEquals(new PurchaseStub().getDateOfPurchase(), purchaseList.getPurchase(4)
                .getDateOfPurchase());
        assertEquals(4, purchaseList.getNumPurchases());
    }

    @Test
    public void addPurchase_middleIndex_addedCorrectly() {
        purchaseList.addSinglePurchase(1, new PurchaseBuilder().build());
        assertEquals(4, purchaseList.getNumPurchases());
        assertEquals(purchaseList.getPurchase(2), new PurchaseBuilder().build());
    }

    @Test
    public void hasPurchase_normalInput_correctResult() {
        assertTrue(purchaseList.hasPurchase(new PurchaseStub()));
    }

    @Test
    public void hasPurchase_nonexistentPurchase_correcResult() {
        assertFalse(purchaseList.hasPurchase(new PurchaseBuilder().build()));
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
        assertEquals(new PurchaseStub().getDateOfPurchase(), removedPurchase.getDateOfPurchase());
        assertEquals(2, purchaseList.getNumPurchases());
    }

    @Test
    public void deletePurchase_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> purchaseList.deletePurchase(4));
        assertEquals(3, purchaseList.getNumPurchases());
    }

    @Test
    public void deletePurchase_deleteCorrectInstance_deletedCorrectly() {
        purchaseList.addSinglePurchase(new PurchaseBuilder().build());
        Purchase deletedPurchase = purchaseList.deletePurchase(new PurchaseBuilder().build());
        assertEquals(deletedPurchase, new PurchaseBuilder().build());
        assertEquals(3, purchaseList.getNumPurchases());
    }

    @Test
    public void deletePurchase_deleteFirstInstance_deletedCorrectly() {
        Purchase deletedPurchase = purchaseList.deletePurchase(new PurchaseStub());
        assertEquals(2, purchaseList.getNumPurchases());
        assertEquals(deletedPurchase, new PurchaseStub());
    }

    @Test
    public void setInstallment_normalInputs_editedCorrectly() {
        Purchase purchase = purchaseList.getPurchase(1);
        Purchase editedPurchase = new PurchaseBuilder()
                .withDescription("Spotify")
                .withMoneySpent("9.50")
                .build();
        purchaseList.setPurchase(purchase, editedPurchase);
        assertEquals(purchaseList.getPurchase(1), editedPurchase);
    }

    @Test
    public void setInstallment_nonExistentInstallment_throwsError() {
        Purchase purchase = new PurchaseBuilder()
                .withDescription("something")
                .build();

        assertThrows(RuntimeException.class, (

        ) -> purchaseList.setPurchase(purchase, new PurchaseStub()));
    }

    @Test
    public void totalSpending_normalInput_addedCorrectly() {
        assertEquals(15.0, purchaseList.totalSpending());
    }

    private static class PurchaseStub extends Purchase {
        public PurchaseStub() {
            super(new PurchaseDescription("lunch at Saizerya"), new PurchaseMoneySpent("5.00"),
                    LocalDate.parse("10/04/2019", Purchase.getDateFormat()));
        }
    }
}
