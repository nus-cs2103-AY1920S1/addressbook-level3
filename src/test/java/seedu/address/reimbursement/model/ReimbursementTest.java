package seedu.address.reimbursement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeadlines.DATE_TIME_FORMATTER;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.person.Person;
import seedu.address.testutil.ReimbursementBuilder;
import seedu.address.testutil.TypicalDeadlines;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.transaction.model.transaction.Transaction;

public class ReimbursementTest {

    private TypicalReimbursements typicalReimbursements = new TypicalReimbursements();

    public ReimbursementTest() {
        typicalReimbursements.resetReimbursements();
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reimbursement(null));
    }

    @Test
    public void constructor_transaction_success() {
        Reimbursement reimbursement = new ReimbursementBuilder().build();
        Person person = TypicalPersons.ALICE;
        assertEquals(person, reimbursement.getPerson());

        ArrayList<Transaction> list = new ArrayList<>();
        Transaction transaction = typicalReimbursements.getAliceTransaction10();
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
        Reimbursement mergedReimbursement = new ReimbursementBuilder().build();
        double totalAmount = reimbursement.getAmount() + mergedReimbursement.getAmount();
        reimbursement.merge(mergedReimbursement);
        assertEquals(totalAmount, reimbursement.getAmount());
    }

    @Test
    public void comparePerson() {
        Reimbursement reimbursementAlice20 = typicalReimbursements.getAliceReimbursement20();
        Reimbursement reimbursementElle = typicalReimbursements.getElleReimbursement100();
        Reimbursement reimbursementAlice30 = typicalReimbursements.getAliceReimbursement30();

        assertTrue(reimbursementAlice20.comparePerson(reimbursementAlice30));
        assertFalse(reimbursementAlice20.comparePerson(reimbursementElle));
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
    public void setDisplayCol() {
        Reimbursement reimbursement = new ReimbursementBuilder().build();

        reimbursement.setIdCol(1);
        assertEquals("1", reimbursement.getIdCol());

        reimbursement.setPersonCol();
        assertEquals(TypicalPersons.ALICE.getName().toString(), reimbursement.getPersonCol());

        reimbursement.setDescriptionCol();
        assertEquals("1. " + typicalReimbursements.getAliceTransaction10().getDescription(),
                reimbursement.getDescriptionCol());

        reimbursement.setDeadlineCol();
        assertEquals(TypicalDeadlines.DEC_DEADLINE.format(DATE_TIME_FORMATTER), reimbursement.getDeadlineCol());
    }

    @Test
    public void reimbursementToString() {
        Reimbursement reimbursementAlice = typicalReimbursements.getAliceReimbursement20();
        Reimbursement reimbursementElle = typicalReimbursements.getElleReimbursement100();

        String strFromToString = "Alice Pauline $-20.00" + System.lineSeparator()
                + "02-Dec-2019" + System.lineSeparator()
                + "1. poster printing";
        assertEquals(strFromToString, reimbursementAlice.toString());

        String strFromToStringNoDeadline = "Elle Meyer $-100.00" + System.lineSeparator()
                + "1. food";
        assertEquals(strFromToStringNoDeadline, reimbursementElle.toString());
    }
}
