package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.jarvis.model.financetracker.InstallmentList;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;
import seedu.jarvis.testutil.finance.InstallmentBuilder;

/**
 * Tests logic of instalment list class.
 */
public class InstallmentListTest {

    private InstallmentList installmentList;

    @BeforeEach
    public void setUp() {
        ObservableList<Installment> listInstallments = FXCollections.observableArrayList();
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
    public void deleteInstallment_deleteCorrectInstance_deletedCorrectly() {
        installmentList.addInstallment(new InstallmentBuilder().build());
        Installment deletedInstallment = installmentList.deleteInstallment(new InstallmentBuilder().build());
        assertEquals(deletedInstallment, new InstallmentBuilder().build());
        assertEquals(3, installmentList.getNumInstallments());
    }

    @Test
    public void deleteInstallment_deleteFirstInstance_deletedCorrectly() {
        Installment deletedInstallment = installmentList.deleteInstallment(new InstallmentStub());
        assertEquals(2, installmentList.getNumInstallments());
        assertEquals(deletedInstallment, new InstallmentStub());
    }

    @Test
    public void setInstallment_normalInputs_editedCorrectly() {
        Installment installment = installmentList.getInstallment(1);
        Installment editedInstallment = new InstallmentBuilder()
                .withDescription("Spotify")
                .withMoneySpent("9.50")
                .build();
        installmentList.setInstallment(installment, editedInstallment);
        assertEquals(installmentList.getInstallment(1), editedInstallment);
    }

    @Test
    public void setInstallment_nonExistentInstallment_throwsError() {
        Installment installment = new InstallmentBuilder()
                .withDescription("something")
                .build();

        assertThrows(RuntimeException.class, (

        ) -> installmentList.setInstallment(installment, new InstallmentStub()));
    }

    @Test
    public void totalMoneySpentOnInstalments_normalInputs_addedCorrectly() {
        assertEquals(28.5, installmentList.getTotalMoneySpentOnInstallments());
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super(new InstallmentDescription("Spotify subscription"), new InstallmentMoneyPaid("9.5"));
        }
    }
}
