package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Instalment;
import seedu.jarvis.model.financetracker.InstalmentList;

/**
 * Tests logic of instalment list class.
 */
public class InstalmentListTest {

    @Test
    public static void getPurchase_normalInput_retrievedCorrectly() {
        ArrayList<Instalment> listPurchases = new ArrayList<>();
        listPurchases.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listPurchases);
        assertEquals(new InstalmentStub().getDescription(), ip.getInstalment(1).getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(),
                ip.getInstalment(1).getMoneySpentOnInstallment());
    }

    @Test
    public static void addInstalment_normalInput_addedCorrectly() {
        InstalmentList ip = new InstalmentList(new ArrayList<>());
        ip.addInstalment(new InstalmentStub());
        assertEquals(new InstalmentStub().getDescription(), ip.getInstalment(1).getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(),
                ip.getInstalment(1).getMoneySpentOnInstallment());
        assertEquals(1, ip.getNumInstalments());
    }

    @Test
    public static void countNumInstalments_normalInput_addedCorrectly() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listInstalments);
        assertEquals(3, ip.getNumInstalments());
    }

    @Test
    public static void deleteInstalment_normalInput_deletedCorrectly() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listInstalments);
        Instalment removedInstalment = ip.deleteInstalment(3);
        assertEquals(new InstalmentStub().getDescription(), removedInstalment.getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(), removedInstalment.getMoneySpentOnInstallment());
        assertEquals(2, ip.getNumInstalments());
    }

    @Test
    public static void deleteInstalment_indexNonexistent_throwsError() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listInstalments);
        assertThrows(IndexOutOfBoundsException.class, () -> ip.deleteInstalment(4));
        assertEquals(3, ip.getNumInstalments());
    }

    @Test
    public static void editInstalment_normalInputs_editedCorrectly() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listInstalments);
        ip.editInstalment(1, "Spotify subscription", 9.50);
        assertEquals("Spotify subscription", ip.getInstalment(1).getDescription());
        assertEquals(9.50, ip.getInstalment(1).getMoneySpentOnInstallment());
    }

    @Test
    public static void editInstalment_indexNonexistent_throwsError() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listInstalments);
        assertThrows(IndexOutOfBoundsException.class, (

        ) -> ip.editInstalment(5, "Spotify", 9.50));
    }

    @Test
    public static void editInstalment_emptyDescription_throwsError() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        listInstalments.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listInstalments);
        assertThrows(NullPointerException.class, (

        ) -> ip.editInstalment(3, null, 9.50));
    }

    @Test
    public static void totalMoneySpentOnInstalments_normalInputs_addedCorrectly() {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub2());
        listInstalments.add(new InstalmentStub2());
        listInstalments.add(new InstalmentStub2());
        listInstalments.add(new InstalmentStub2());
        listInstalments.add(new InstalmentStub2());
        InstalmentList ip = new InstalmentList(listInstalments);
        assertEquals(65.0, ip.getTotalMoneySpentOnInstalments());
    }
}

class InstalmentStub2 extends Instalment {
    public InstalmentStub2() {
        super("Netflix subscription", 13.0);
    }
}
