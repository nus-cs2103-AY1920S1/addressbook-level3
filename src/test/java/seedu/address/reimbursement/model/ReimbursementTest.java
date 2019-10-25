package seedu.address.reimbursement.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.person.Person;
import seedu.address.testutil.ReimbursementBuilder;
import seedu.address.testutil.TypicalDeadlines;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Transaction;

public class ReimbursementTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reimbursement(null));
    }

    @Test
    public void constructor_from_transaction() {
        Reimbursement reimbursement = new ReimbursementBuilder().build();
        Person person = TypicalPersons.ALICE;
        assertEquals(person, reimbursement.getPerson());

        ArrayList<Transaction> list = new ArrayList<>();
        Transaction transaction = TypicalTransactions.ALICE_TRANSACTION_10;
        list.add(transaction);
        assertEquals(list, reimbursement.getList());

        assertEquals(transaction.getAmount(), reimbursement.getAmount());

        assertEquals(TypicalDeadlines.DEC_DEADLINE, reimbursement.getDeadline());
    }

    @Test
    public void setPerson() {
        Person person = TypicalPersons.BOB;
        Reimbursement reimbursement = new ReimbursementBuilder().build();
        reimbursement.setPerson(person);
        assertEquals(person, reimbursement.getPerson());
    }

    @Test
    public void merge() {
        Reimbursement reimbursement = new ReimbursementBuilder().build();
        Reimbursement mergedReimbursement = TypicalReimbursements.ALICE_REIMBURSEMENT_20;
        double totalAmount = reimbursement.getAmount() + mergedReimbursement.getAmount();
        reimbursement.merge(mergedReimbursement);
        assertEquals(totalAmount, reimbursement.getAmount());
    }

    @Test
    public void comparePerson() {
        Reimbursement reimbursement_alice_20 = TypicalReimbursements.ALICE_REIMBURSEMENT_20;
        Reimbursement reimbursement_elle = TypicalReimbursements.ELLE_REIMBURSEMENT_100;
        Reimbursement reimbursement_alice_30 = TypicalReimbursements.ALICE_REIMBURSEMENT_30;

        assertTrue(reimbursement_alice_20.comparePerson(reimbursement_alice_30));
        assertFalse(reimbursement_alice_20.comparePerson(reimbursement_elle));
    }

    @Test
    public void done() {
        Reimbursement reimbursement = new ReimbursementBuilder().build();
        reimbursement.done();
        ArrayList<Transaction> list = reimbursement.getList();
        for (Transaction trans : list) {
            assertTrue(trans.getStatus());
        }
    }

    @Test
    public void ui_display() {
        Reimbursement reimbursement = new ReimbursementBuilder().build();

        reimbursement.setIdCol(1);
        assertEquals("1", reimbursement.getIdCol());

        reimbursement.setPersonCol();
        assertEquals(TypicalPersons.ALICE.getName().toString(), reimbursement.getPersonCol());


    }
}
