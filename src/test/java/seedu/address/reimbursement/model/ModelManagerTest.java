package seedu.address.reimbursement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.testutil.ReimbursementBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.model.Transaction;

public class ModelManagerTest {
    //initialization
    private Transaction transactionAlice
            = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
    private Transaction transactionBob
            = new TransactionBuilder(TypicalPersons.BOB).withAmount(-20).build();
    private Transaction transactionAmy
            = new TransactionBuilder(TypicalPersons.AMY).withAmount(-30).build();

    private Reimbursement reimbursementAlice
            = new ReimbursementBuilder(transactionAlice).build();
    private Reimbursement reimbursementBob
            = new ReimbursementBuilder(transactionBob).build();
    private Reimbursement reimbursementAmy
            = new ReimbursementBuilder(transactionAmy).build();

    private ArrayList<Reimbursement> arrList_alice_bob = new ArrayList<>(Arrays.asList(reimbursementAlice,
            reimbursementBob));
    private ReimbursementList list_alice_bob = new ReimbursementList(arrList_alice_bob);

    private ArrayList<Reimbursement> arrList_alice_amy = new ArrayList<>(Arrays.asList(reimbursementAlice,
            reimbursementAmy));
    private ReimbursementList list_alice_amy = new ReimbursementList(arrList_alice_amy);

    private ModelManager modelManager = new ModelManager(list_alice_amy);

    @Test
    public void update_list() {
        assertEquals(list_alice_amy, modelManager.getReimbursementList());
        modelManager.updateReimbursementList(list_alice_bob);
        assertEquals(list_alice_bob, modelManager.getReimbursementList());
    }

    @Test
    public void edit_person_operation() {
        modelManager.updateReimbursementList(TypicalPersons.AMY, TypicalPersons.BOB);
        assertEquals(list_alice_amy, modelManager.getReimbursementList());
    }

    @Test
    public void find_reimbursement() {
        try{
            assertEquals(reimbursementAlice, modelManager.findReimbursement(TypicalPersons.ALICE));
        }catch(NoSuchPersonReimbursementException e) {
            fail();
        }


    }

}
