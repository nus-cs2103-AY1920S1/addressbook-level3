package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Instalment;
import seedu.jarvis.model.financetracker.InstalmentList;

/**
 * Tests logic of instalment list class.
 */
public class InstalmentListTest {

    private InstalmentList ip;

    @BeforeEach
    public void setUp() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        ip = new InstalmentList(listInstalments);
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {
        assertEquals(new InstalmentStub().getDescription(), ip.getInstalment(1).getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(),
                ip.getInstalment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void addInstalment_normalInput_addedCorrectly() {
        ip.addInstalment(new InstalmentStub());
        assertEquals(new InstalmentStub().getDescription(), ip.getInstalment(1).getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(),
                ip.getInstalment(1).getMoneySpentOnInstallment());
        assertEquals(4, ip.getNumInstalments());
    }

    @Test
    public void countNumInstalments_normalInput_addedCorrectly() {
        assertEquals(3, ip.getNumInstalments());
    }

    @Test
    public void deleteInstalment_normalInput_deletedCorrectly() {
        Instalment removedInstalment = ip.deleteInstalment(3);
        assertEquals(new InstalmentStub().getDescription(), removedInstalment.getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(), removedInstalment.getMoneySpentOnInstallment());
        assertEquals(2, ip.getNumInstalments());
    }

    @Test
    public void deleteInstalment_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> ip.deleteInstalment(4));
        assertEquals(3, ip.getNumInstalments());
    }

    @Test
    public void editInstalment_normalInputs_editedCorrectly() {
        ip.editInstalment(1, "Spotify subscription", 9.50);
        assertEquals("Spotify subscription", ip.getInstalment(1).getDescription());
        assertEquals(9.50, ip.getInstalment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void editInstalment_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, (

        ) -> ip.editInstalment(5, "Spotify", 9.50));
    }

    @Test
    public void editInstalment_emptyDescription_throwsError() {
        assertThrows(NullPointerException.class, (

        ) -> ip.editInstalment(3, null, 9.50));
    }

    @Test
    public void totalMoneySpentOnInstalments_normalInputs_addedCorrectly() {
        assertEquals(28.5, ip.getTotalMoneySpentOnInstalments());
    }
}
