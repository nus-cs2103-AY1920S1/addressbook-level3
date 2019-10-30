package seedu.address.reimbursement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.testutil.ReimbursementBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalDeadlines;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;


public class ReimbursementListTest {
    private TypicalReimbursements typicalReimbursements = new TypicalReimbursements();

    public ReimbursementListTest() {
        typicalReimbursements.resetReimbursements();
    }

    @Test
    public void constructor_empty_success() {
        ReimbursementList reimbursementList = new ReimbursementList();
        assertEquals(new ArrayList<Reimbursement>(), reimbursementList.getList());
    }

    @Test
    public void constructor_transactionList_success() {
        TransactionList transList = new TransactionList();
        transList.add(typicalReimbursements.getAliceTransaction10());
        transList.add(typicalReimbursements.getAliceTransaction12());
        transList.add(typicalReimbursements.getElleTransaction11());
        ReimbursementList list = new ReimbursementList(transList);

        assertEquals(-50, list.get(0).getAmount());

        assertEquals(2, list.size());
    }

    @Test
    public void updatePerson() {
        Transaction transaction = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Reimbursement reimbursement = new ReimbursementBuilder(transaction).build();

        ArrayList<Reimbursement> arrList = new ArrayList<>();
        arrList.add(reimbursement);

        ReimbursementList list = new ReimbursementList(arrList);

        list.updatePerson(TypicalPersons.BOB, TypicalPersons.ALICE);
        assertEquals(TypicalPersons.BOB, list.get(0).getPerson());

        Reimbursement found = null;
        try {
            found = list.findReimbursement(TypicalPersons.BOB);
        } catch (NoSuchPersonReimbursementException e) {
            fail();
        }
        assertEquals(found, reimbursement);

        try {
            found = list.findReimbursement(TypicalPersons.ELLE);
        } catch (NoSuchPersonReimbursementException e) {
            assertTrue(true);
        }

        try {
            found = list.doneReimbursement(TypicalPersons.BOB);
        } catch (NoSuchPersonReimbursementException e) {
            fail();
        }
        assertEquals(0, list.size());

    }

    @Test
    public void sortReimbursement() {
        Transaction transactionAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-10).build();
        Transaction transactionBob = new TransactionBuilder(TypicalPersons.BOB).withAmount(-20).build();
        Reimbursement reimbursementAlice = new ReimbursementBuilder(transactionAlice).build();
        Reimbursement reimbursementBob = new ReimbursementBuilder(transactionBob).build();

        ArrayList<Reimbursement> arrList = new ArrayList<>(Arrays.asList(reimbursementAlice, reimbursementBob));
        ReimbursementList list = new ReimbursementList(arrList);

        list.sortByAmount();
        assertEquals(reimbursementBob, list.get(0));

        list.sortByName();
        assertEquals(reimbursementAlice, list.get(0));

        try {
            list.addDeadline(TypicalPersons.BOB, TypicalDeadlines.DEC_DEADLINE);
            list.sortByDeadline();
        } catch (Exception e) {
            fail();
        }
        assertEquals(reimbursementBob, list.get(0));

        try {
            list.addDeadline(TypicalPersons.ALICE, TypicalDeadlines.NOV_DEADLINE);
            list.sortByDeadline();
        } catch (Exception e) {
            fail();
        }
        assertEquals(reimbursementAlice, list.get(0));

        assertEquals(reimbursementAlice.toString() + System.lineSeparator() + reimbursementBob.toString(),
                list.toString());

    }

}
