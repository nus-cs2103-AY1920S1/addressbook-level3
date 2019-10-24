package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.MonthlyLimit;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.financetracker.purchase.PurchaseDescription;
import seedu.jarvis.model.financetracker.purchase.PurchaseMoneySpent;
import seedu.jarvis.testutil.finance.InstallmentBuilder;
import seedu.jarvis.testutil.finance.PurchaseBuilder;


/**
 * Tests logic of finance tracker class.
 */
public class FinanceTrackerTest {

    private FinanceTracker financeTracker;

    @BeforeEach
    public void setUp() {
        financeTracker = new FinanceTracker();
        ObservableList<Purchase> allPurchases = FXCollections.observableArrayList();
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        financeTracker.setPurchaseList(allPurchases);
        ObservableList<Installment> allInstalments = FXCollections.observableArrayList();;
        allInstalments.add(new InstallmentStub());
        allInstalments.add(new InstallmentStub());
        allInstalments.add(new InstallmentStub());
        financeTracker.setInstallmentList(allInstalments);
    }

    @Test
    public void addPurchase_normalInput_addedCorrectly() {
        financeTracker.addSinglePurchase(new PurchaseStub());
        Purchase addedPurchase = financeTracker.getPurchase(4);
        assertEquals(new PurchaseStub().getDescription(), addedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), addedPurchase.getMoneySpent());
        assertEquals(new PurchaseStub().getDateOfPurchase(), addedPurchase.getDateOfPurchase());
        assertEquals(4, financeTracker.getTotalPurchases());
    }

    @Test
    public void addPurchaseWithIndex_normalInput_addedCorrectly() {
        Purchase newPurchase = new PurchaseBuilder().withDescription("Gong Cha").build();
        financeTracker.addSinglePurchase(2, newPurchase);
        assertEquals(4, financeTracker.getNumPurchases());
        assertEquals(newPurchase, financeTracker.getPurchase(3));
        assertEquals(new PurchaseStub(), financeTracker.getPurchase(4));
    }

    @Test
    public void deletePurchase_normalInput_deletedCorrectly() {
        Purchase deletedPurchase = financeTracker.deleteSinglePurchase(2);
        assertEquals(new PurchaseStub().getDescription(), deletedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), deletedPurchase.getMoneySpent());
        assertEquals(new PurchaseStub().getDateOfPurchase(), deletedPurchase.getDateOfPurchase());
        assertEquals(2, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePurchase_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.deleteSinglePurchase(4));
        assertEquals(3, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePurchase_deleteCorrectInstance_deletedCorrectly() {
        financeTracker.addSinglePurchase(new PurchaseBuilder().build());
        Purchase deletedPurchase = financeTracker.deleteSinglePurchase(new PurchaseBuilder().build());
        assertEquals(deletedPurchase, new PurchaseBuilder().build());
        assertEquals(3, financeTracker.getNumPurchases());
    }

    @Test
    public void deletePurchase_deleteFirstInstance_deletedCorrectly() {
        Purchase deletedPurchase = financeTracker.deleteSinglePurchase(new PurchaseStub());
        assertEquals(2, financeTracker.getNumPurchases());
        assertEquals(deletedPurchase, new PurchaseStub());
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {
        assertEquals(financeTracker.getPurchase(2), new PurchaseStub());
    }

    @Test
    public void getPurchase_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.getPurchase(5));
    }

    @Test
    public void getNumPurchases_correctResult() {
        assertEquals(3, financeTracker.getNumPurchases());
    }

    @Test
    public void hasPurchase_existingPurchase_correctResult() {
        assertTrue(financeTracker.hasPurchase(new PurchaseStub()));
    }

    @Test
    public void hasPurchase_nonexistentPurchase_correctResult() {
        assertFalse(financeTracker.hasPurchase(new PurchaseBuilder().build()));
    }

    @Test
    public void addInstallment_normalInput_addedCorrectly() {
        financeTracker.addInstallment(new InstallmentStub());
        Installment addedInstalment = financeTracker.getInstallment(4);
        assertEquals(new InstallmentStub().getDescription(), addedInstalment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(), addedInstalment.getMoneySpentOnInstallment());
        assertEquals(4, financeTracker.getNumInstallments());
    }

    @Test
    public void deleteInstallment_normalInput_deletedCorrectly() {
        Installment deletedInstallment = financeTracker.deleteInstallment(2);
        assertEquals(new InstallmentStub().getDescription(), deletedInstallment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                deletedInstallment.getMoneySpentOnInstallment());
        assertEquals(2, financeTracker.getNumInstallments());
    }

    @Test
    public void deleteInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.deleteInstallment(4));
        assertEquals(3, financeTracker.getNumInstallments());
    }

    @Test
    public void deleteInstallment_deleteCorrectInstance_deletedCorrectly() {
        financeTracker.addInstallment(new InstallmentBuilder().build());
        Installment deletedInstallment = financeTracker.deleteInstallment(new InstallmentBuilder().build());
        assertEquals(deletedInstallment, new InstallmentBuilder().build());
        assertEquals(3, financeTracker.getNumInstallments());
    }

    @Test
    public void deleteInstallment_deleteFirstInstance_deletedCorrectly() {
        Installment deletedInstallment = financeTracker.deleteInstallment(new InstallmentStub());
        assertEquals(2, financeTracker.getNumInstallments());
        assertEquals(deletedInstallment, new InstallmentStub());
    }

    @Test
    public void editInstallment_normalInputs_editedCorrectly() {
        Installment installment = financeTracker.getInstallment(1);
        Installment editedInstallment = new InstallmentBuilder()
                                                .withDescription("Spotify")
                                                .withMoneySpent("9.50")
                                                .build();
        financeTracker.setInstallment(installment, editedInstallment);
        assertEquals(financeTracker.getInstallment(1), editedInstallment);
    }

    @Test
    public void editInstallment_nonExistentInstallment_throwsError() {
        Installment installment = new InstallmentBuilder()
                                            .withDescription("something")
                                            .build();

        assertThrows(RuntimeException.class, (

        ) -> financeTracker.setInstallment(installment, new InstallmentStub()));
    }

    @Test
    public void getInstallment_normalInput_retrievedCorrectly() {
        assertEquals(financeTracker.getInstallment(2), new InstallmentStub());
    }

    @Test
    public void getInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.getInstallment(5));
    }

    @Test
    public void setMonthlyLimit_normalInput_updatedCorrectly() {
        financeTracker.setMonthlyLimit(new MonthlyLimit("500.0"));
        assertEquals((double) Optional.of(500.0).get(), financeTracker.getMonthlyLimit().get().getMonthlyLimit());
    }

    @Test
    public void setMonthlyLimit_negativeNumber_errorThrown() {
        assertThrows(RuntimeException.class, (

                ) -> financeTracker.setMonthlyLimit(new MonthlyLimit("-500")));
    }

    private static class PurchaseStub extends Purchase {
        public PurchaseStub() {
            super(new PurchaseDescription("lunch at Saizerya"), new PurchaseMoneySpent("5.00"),
                    LocalDate.parse("10/04/2019", Purchase.getDateFormat()));
        }
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super(new InstallmentDescription("Spotify subscription"), new InstallmentMoneyPaid("9.5"));
        }
    }
}


