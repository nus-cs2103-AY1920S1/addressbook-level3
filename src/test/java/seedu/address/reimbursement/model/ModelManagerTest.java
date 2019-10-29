package seedu.address.reimbursement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.testutil.ReimbursementBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalDeadlines;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.transaction.model.transaction.Transaction;


public class ModelManagerTest {
    //initialization
    private Transaction transactionAlice;
    private Transaction transactionBob;
    private Transaction transactionAmy;

    private Reimbursement reimbursementAlice;
    private Reimbursement reimbursementBob;
    private Reimbursement reimbursementAmy;

    private ArrayList<Reimbursement> arrListAliceBob;
    private ReimbursementList listAliceBob;

    private ArrayList<Reimbursement> arrListAliceAmy;
    private ReimbursementList listAliceAmy;

    private ModelManager modelManager;
    private TypicalReimbursements typicalReimbursements = new TypicalReimbursements();

    public ModelManagerTest() {
        typicalReimbursements.resetReimbursements();

        transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        transactionBob = new TransactionBuilder(TypicalPersons.BOB).withAmount(-20).build();
        transactionAmy = new TransactionBuilder(TypicalPersons.AMY).withAmount(-30).build();

        reimbursementAlice = new ReimbursementBuilder(transactionAlice).build();
        reimbursementBob = new ReimbursementBuilder(transactionBob).build();
        reimbursementAmy = new ReimbursementBuilder(transactionAmy).build();

        arrListAliceBob = new ArrayList<>(Arrays.asList(reimbursementAlice,
                reimbursementBob));
        listAliceBob = new ReimbursementList(arrListAliceBob);

        arrListAliceAmy = new ArrayList<>(Arrays.asList(reimbursementAlice,
                reimbursementAmy));
        listAliceAmy = new ReimbursementList(arrListAliceAmy);

        modelManager = new ModelManager(listAliceAmy);
    }

    @Test
    public void updateReimbursementList_transactionListChange_success() {
        assertEquals(listAliceAmy, modelManager.getReimbursementList());
        modelManager.updateReimbursementList(listAliceBob);
        assertEquals(listAliceBob, modelManager.getReimbursementList());
    }

    @Test
    public void updateReimbursementList_personChange_success() {
        modelManager.updateReimbursementList(TypicalPersons.AMY, TypicalPersons.BOB);
        assertEquals(listAliceAmy, modelManager.getReimbursementList());
    }

    @Test
    public void findReimbursement() {
        try {
            assertEquals(reimbursementAlice, modelManager.findReimbursement(TypicalPersons.ALICE));
        } catch (NoSuchPersonReimbursementException e) {
            fail();
        }

        assertEquals(reimbursementAlice, modelManager.getFilteredReimbursementList().get(0));
        assertEquals(1, modelManager.getFilteredReimbursementList().size());

        modelManager.listReimbursement();
        assertEquals(listAliceAmy, modelManager.getFilteredReimbursementList());

    }

    @Test
    public void sortList() {
        modelManager.sortListByAmount();
        assertEquals(reimbursementAmy, modelManager.getFilteredReimbursementList().get(0));

        modelManager.sortListByName();
        assertEquals(reimbursementAlice, modelManager.getFilteredReimbursementList().get(0));

        try {
            modelManager.addDeadline(TypicalPersons.AMY, TypicalDeadlines.DEC_DEADLINE);
        } catch (Exception e) {
            fail();
        }
        modelManager.sortListByDeadline();
        assertEquals(reimbursementAmy, modelManager.getFilteredReimbursementList().get(0));
    }

    @Test
    public void doneReimbursement() {

        try {
            modelManager.doneReimbursement(TypicalPersons.AMY);
        } catch (Exception e) {
            fail();
        }
        assertThrows(Exception.class, () -> modelManager.findReimbursement(TypicalPersons.AMY));
    }

}
