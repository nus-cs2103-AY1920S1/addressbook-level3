package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.Instalment;
import seedu.jarvis.model.financetracker.InstalmentList;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.PurchaseList;

/**
 * Tests logic of finance tracker class.
 */
public class FinanceTrackerTest {

    /**
     * Runs all tests in this test class.
     * @param args
     */
    public static void main(String[] args) {
        addPayment_normalInput_addedCorrectly();
        deletePayment_normalInput_deletedCorrectly();
        deletePayment_indexNonexistent_throwsError();
        addInstalment_normalInput_addedCorrectly();
        deleteInstalment_normalInput_deletedCorrectly();
        deleteInstalment_indexNonexistent_throwsError();
        editInstalment_normalInputs_editedCorrectly();
        editInstalment_indexNonexistent_throwsError();
        editInstalment_emptyDescription_throwsError();
        setMonthlyLimit_normalInput_updatedCorrectly();
    }

    @Test
    public static void addPayment_normalInput_addedCorrectly() {
        FinanceTracker financeTracker = new FinanceTracker();
        financeTracker.addSinglePayment(new PurchaseStub2());
        Purchase addedPurchase = financeTracker.getPayment(1);
        assertEquals(new PurchaseStub2().getDescription(), addedPurchase.getDescription());
        assertEquals(new PurchaseStub2().getMoneySpent(), addedPurchase.getMoneySpent());
        assertEquals(1, financeTracker.getTotalPurchases());
    }

    @Test
    public static void deletePayment_normalInput_deletedCorrectly() {
        FinanceTracker financeTracker = new FinanceTracker();
        ArrayList<Purchase> allPurchases = new ArrayList<>();
        allPurchases.add(new PurchaseStub2());
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub2());
        financeTracker.setPurchaseList(new PurchaseListStub(allPurchases));
        Purchase deletedPurchase = financeTracker.deleteSinglePayment(2);
        assertEquals(new PurchaseStub().getDescription(), deletedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), deletedPurchase.getMoneySpent());
        assertEquals(2, financeTracker.getTotalPurchases());
    }

    @Test
    public static void deletePayment_indexNonexistent_throwsError() {
        FinanceTracker financeTracker = new FinanceTracker();
        ArrayList<Purchase> allPurchases = new ArrayList<>();
        allPurchases.add(new PurchaseStub2());
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub2());
        financeTracker.setPurchaseList(new PurchaseListStub(allPurchases));
        assertThrows(IndexOutOfBoundsException.class, () -> financeTracker.deleteSinglePayment(4));
        assertEquals(3, financeTracker.getTotalPurchases());
    }

    @Test
    public static void addInstalment_normalInput_addedCorrectly() {
        FinanceTracker financeTracker = new FinanceTracker();
        financeTracker.addInstalment(new InstalmentStub());
        Instalment addedInstalment = financeTracker.getInstalment(1);
        assertEquals(new InstalmentStub().getDescription(), addedInstalment.getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(), addedInstalment.getMoneySpentOnInstallment());
        assertEquals(1, financeTracker.getTotalInstalments());
    }

    @Test
    public static void deleteInstalment_normalInput_deletedCorrectly() {
        FinanceTracker financeTracker = new FinanceTracker();
        ArrayList<Instalment> allInstalments = new ArrayList<>();
        allInstalments.add(new InstalmentStub());
        allInstalments.add(new InstalmentStub2());
        allInstalments.add(new InstalmentStub());
        financeTracker.setInstalmentList(new InstalmentListStub(allInstalments));
        Instalment deletedInstalment = financeTracker.deleteInstalment(2);
        assertEquals(new InstalmentStub2().getDescription(), deletedInstalment.getDescription());
        assertEquals(new InstalmentStub2().getMoneySpentOnInstallment(),
                deletedInstalment.getMoneySpentOnInstallment());
        assertEquals(2, financeTracker.getTotalInstalments());
    }

    @Test
    public static void deleteInstalment_indexNonexistent_throwsError() {
        FinanceTracker financeTracker = new FinanceTracker();
        ArrayList<Instalment> allInstalments = new ArrayList<>();
        allInstalments.add(new InstalmentStub());
        allInstalments.add(new InstalmentStub2());
        allInstalments.add(new InstalmentStub());
        financeTracker.setInstalmentList(new InstalmentListStub(allInstalments));
        assertThrows(IndexOutOfBoundsException.class, () -> financeTracker.deleteInstalment(4));
        assertEquals(3, financeTracker.getTotalInstalments());
    }

    @Test
    public static void editInstalment_normalInputs_editedCorrectly() {
        FinanceTracker financeTracker = new FinanceTracker();
        ArrayList<Instalment> allInstalments = new ArrayList<>();
        allInstalments.add(new InstalmentStub());
        allInstalments.add(new InstalmentStub2());
        allInstalments.add(new InstalmentStub());
        financeTracker.setInstalmentList(new InstalmentListStub(allInstalments));
        financeTracker.editInstalment(1, "Student price Spotify subscription", 7.50);
        assertEquals("Student price Spotify subscription",
                financeTracker.getInstalment(1).getDescription());
        assertEquals(7.50, financeTracker.getInstalment(1).getMoneySpentOnInstallment());
    }

    @Test
    public static void editInstalment_indexNonexistent_throwsError() {
        FinanceTracker financeTracker = new FinanceTracker();
        ArrayList<Instalment> allInstalments = new ArrayList<>();
        allInstalments.add(new InstalmentStub());
        allInstalments.add(new InstalmentStub2());
        allInstalments.add(new InstalmentStub());
        financeTracker.setInstalmentList(new InstalmentListStub(allInstalments));
        assertThrows(IndexOutOfBoundsException.class, (

        ) -> financeTracker.editInstalment(5, "Spotify", 9.50));
    }

    @Test
    public static void editInstalment_emptyDescription_throwsError() {
        FinanceTracker financeTracker = new FinanceTracker();
        ArrayList<Instalment> allInstalments = new ArrayList<>();
        allInstalments.add(new InstalmentStub());
        allInstalments.add(new InstalmentStub2());
        allInstalments.add(new InstalmentStub());
        financeTracker.setInstalmentList(new InstalmentListStub(allInstalments));
        assertThrows(NullPointerException.class, (

            ) -> financeTracker.editInstalment(3, null, 9.50));
    }

    @Test
    public static void setMonthlyLimit_normalInput_updatedCorrectly() {
        FinanceTracker financeTracker = new FinanceTracker();
        financeTracker.setMonthlyLimit(500.0);
        assertEquals(500.0, financeTracker.getMonthlyLimit());
    }

}

class PurchaseStub2 extends Purchase {
    public PurchaseStub2() {
        super("lunch at deck", 5.00);
    }
}

class PurchaseListStub extends PurchaseList {
    public PurchaseListStub(ArrayList<Purchase> purchases) {
        super(purchases);
    }
}

class InstalmentListStub extends InstalmentList {
    public InstalmentListStub(ArrayList<Instalment> instalments) {
        super(instalments);
    }
}
