package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.Installment;
import seedu.jarvis.model.financetracker.InstallmentList;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.PurchaseList;

/**
 * Tests logic of finance tracker class.
 */
public class FinanceTrackerTest {

    private FinanceTracker financeTracker;

    @BeforeEach
    public void setUp() {
        financeTracker = new FinanceTracker();
        ArrayList<Purchase> allPurchases = new ArrayList<>();
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        financeTracker.setPurchaseList(new PurchaseList(allPurchases));
        ArrayList<Installment> allInstalments = new ArrayList<>();
        allInstalments.add(new InstallmentStub());
        allInstalments.add(new InstallmentStub());
        allInstalments.add(new InstallmentStub());
        financeTracker.setInstallmentList(new InstallmentList(allInstalments));
    }

    @Test
    public void addPayment_normalInput_addedCorrectly() {
        financeTracker.addSinglePayment(new PurchaseStub());
        Purchase addedPurchase = financeTracker.getPayment(4);
        assertEquals(new PurchaseStub().getDescription(), addedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), addedPurchase.getMoneySpent());
        assertEquals(4, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePayment_normalInput_deletedCorrectly() {
        Purchase deletedPurchase = financeTracker.deleteSinglePayment(2);
        assertEquals(new PurchaseStub().getDescription(), deletedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), deletedPurchase.getMoneySpent());
        assertEquals(2, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePayment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.deleteSinglePayment(4));
        assertEquals(3, financeTracker.getTotalPurchases());
    }

    @Test
    public void addInstallment_normalInput_addedCorrectly() {
        financeTracker.addInstallment(new InstallmentStub());
        Installment addedInstalment = financeTracker.getInstallment(4);
        assertEquals(new InstallmentStub().getDescription(), addedInstalment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(), addedInstalment.getMoneySpentOnInstallment());
        assertEquals(4, financeTracker.getTotalInstallments());
    }

    @Test
    public void deleteInstallment_normalInput_deletedCorrectly() {
        Installment deletedInstallment = financeTracker.deleteInstallment(2);
        assertEquals(new InstallmentStub().getDescription(), deletedInstallment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                deletedInstallment.getMoneySpentOnInstallment());
        assertEquals(2, financeTracker.getTotalInstallments());
    }

    @Test
    public void deleteInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.deleteInstallment(4));
        assertEquals(3, financeTracker.getTotalInstallments());
    }

    @Test
    public void editInstallment_normalInputs_editedCorrectly() {
        financeTracker.editInstallment(1,
                "Student price Spotify subscription", 7.50);
        assertEquals("Student price Spotify subscription",
                financeTracker.getInstallment(1).getDescription());
        assertEquals(7.50, financeTracker.getInstallment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void editInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, (

        ) -> financeTracker.editInstallment(5, "Spotify", 9.50));
    }

    @Test
    public void editInstallment_emptyDescription_throwsError() {
        assertThrows(NullPointerException.class, (

            ) -> financeTracker.editInstallment(3, null, 9.50));
    }

    @Test
    public void setMonthlyLimit_normalInput_updatedCorrectly() {
        financeTracker.setMonthlyLimit(500.0);
        assertEquals(500.0, financeTracker.getMonthlyLimit());
    }

    @Test
    public void setMonthlyLimit_negativeNumber_errorThrown() {
        assertThrows(RuntimeException.class, (

                ) -> financeTracker.setMonthlyLimit(-500));
    }

    private static class PurchaseStub extends Purchase {
        public PurchaseStub() {
            super("lunch at deck", 5.00);
        }
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super("Spotify subscription", 9.5);
        }
    }
}


