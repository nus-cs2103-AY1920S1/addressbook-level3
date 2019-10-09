package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Installment;
import seedu.jarvis.model.financetracker.InstallmentList;

/**
 * Tests logic of instalment list class.
 */
public class InstallmentListTest {

    private InstallmentList installmentList;

    @BeforeEach
    public void setUp() {
        ArrayList<Installment> listInstallments = new ArrayList<>();
        listInstallments.add(new InstallmentStub());
        listInstallments.add(new InstallmentStub());
        listInstallments.add(new InstallmentStub());
        installmentList = new InstallmentList(listInstallments);
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {
        assertEquals(new InstallmentStub().getDescription(), installmentList.getInstallment(1).getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                installmentList.getInstallment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void addInstallment_normalInput_addedCorrectly() {
        installmentList.addInstallment(new InstallmentStub());
        assertEquals(new InstallmentStub().getDescription(), installmentList.getInstallment(1).getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                installmentList.getInstallment(1).getMoneySpentOnInstallment());
        assertEquals(4, installmentList.getNumInstallments());
    }

    @Test
    public void countNumInstallments_normalInput_addedCorrectly() {
        assertEquals(3, installmentList.getNumInstallments());
    }

    @Test
    public void deleteInstallment_normalInput_deletedCorrectly() {
        Installment removedInstallment = installmentList.deleteInstallment(3);
        assertEquals(new InstallmentStub().getDescription(), removedInstallment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                removedInstallment.getMoneySpentOnInstallment());
        assertEquals(2, installmentList.getNumInstallments());
    }

    @Test
    public void deleteInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> installmentList.deleteInstallment(4));
        assertEquals(3, installmentList.getNumInstallments());
    }

    @Test
    public void editInstallment_normalInputs_editedCorrectly() {
        installmentList.editInstallment(1, "Spotify subscription", 13.0);
        assertEquals("Spotify subscription", installmentList.getInstallment(1).getDescription());
        assertEquals(13.0, installmentList.getInstallment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void editInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, (

        ) -> installmentList.editInstallment(5, "Spotify", 9.50));
    }

    @Test
    public void editInstalment_emptyDescription_throwsError() {
        assertThrows(NullPointerException.class, (

        ) -> installmentList.editInstallment(3, null, 9.50));
    }

    @Test
    public void totalMoneySpentOnInstalments_normalInputs_addedCorrectly() {
        assertEquals(28.5, installmentList.getTotalMoneySpentOnInstallments());
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super("Spotify subscription", 9.5);
        }
    }
}
