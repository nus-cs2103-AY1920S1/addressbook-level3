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

    private InstallmentList ip;

    @BeforeEach
    public void setUp() {
        ArrayList<Installment> listInstallments = new ArrayList<>();
        listInstallments.add(new InstallmentStub());
        listInstallments.add(new InstallmentStub());
        listInstallments.add(new InstallmentStub());
        ip = new InstallmentList(listInstallments);
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {
        assertEquals(new InstallmentStub().getDescription(), ip.getInstallment(1).getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                ip.getInstallment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void addInstallment_normalInput_addedCorrectly() {
        ip.addInstallment(new InstallmentStub());
        assertEquals(new InstallmentStub().getDescription(), ip.getInstallment(1).getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                ip.getInstallment(1).getMoneySpentOnInstallment());
        assertEquals(4, ip.getNumInstallments());
    }

    @Test
    public void countNumInstallments_normalInput_addedCorrectly() {
        assertEquals(3, ip.getNumInstallments());
    }

    @Test
    public void deleteInstallment_normalInput_deletedCorrectly() {
        Installment removedInstallment = ip.deleteInstallment(3);
        assertEquals(new InstallmentStub().getDescription(), removedInstallment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                removedInstallment.getMoneySpentOnInstallment());
        assertEquals(2, ip.getNumInstallments());
    }

    @Test
    public void deleteInstallment_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> ip.deleteInstallment(4));
        assertEquals(3, ip.getNumInstallments());
    }

    @Test
    public void editInstallment_normalInputs_editedCorrectly() {
        ip.editInstallment(1, "Spotify subscription", 9.50);
        assertEquals("Spotify subscription", ip.getInstallment(1).getDescription());
        assertEquals(9.50, ip.getInstallment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void editInstallment_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, (

        ) -> ip.editInstallment(5, "Spotify", 9.50));
    }

    @Test
    public void editInstalment_emptyDescription_throwsError() {
        assertThrows(NullPointerException.class, (

        ) -> ip.editInstallment(3, null, 9.50));
    }

    @Test
    public void totalMoneySpentOnInstalments_normalInputs_addedCorrectly() {
        assertEquals(28.5, ip.getTotalMoneySpentOnInstallments());
    }
}
