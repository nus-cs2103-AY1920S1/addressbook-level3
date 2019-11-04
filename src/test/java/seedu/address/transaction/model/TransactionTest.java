package seedu.address.transaction.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.transaction.model.transaction.Transaction.DECIMAL_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.transaction.model.transaction.Transaction;

class TransactionTest {
    @Test
    public void equals() {
        // same values -> returns true
        Transaction aliceCopy = new TransactionBuilder(TypicalPersons.ALICE).build();
        Transaction transaction = new TransactionBuilder(TypicalPersons.ALICE).build();
        Transaction diffTrans = new TransactionBuilder(TypicalPersons.BOB).build();
        assertTrue(transaction.equals(aliceCopy));

        // same object -> returns true
        assertTrue(transaction.equals(transaction));

        // null -> returns false
        assertFalse(transaction.equals(null));

        // different type -> returns false
        assertFalse(transaction.equals(5));

        // different person -> returns false
        assertFalse(transaction.equals(diffTrans));

        // different name -> returns false
        Transaction editedAlice = new TransactionBuilder(TypicalPersons.ALICE).withPerson(TypicalPersons.CARL).build();
        assertFalse(transaction.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new TransactionBuilder(TypicalPersons.ALICE).withAmount(999).build();
        assertFalse(transaction.equals(editedAlice));

        // different email -> returns false
        editedAlice = new TransactionBuilder(TypicalPersons.ALICE).withCategory("wrong").build();
        assertFalse(transaction.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TransactionBuilder(TypicalPersons.ALICE).withDescription("wrong too").build();
        assertFalse(transaction.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TransactionBuilder(TypicalPersons.ALICE).withDate("01-Jun-2019").build();
        assertFalse(transaction.equals(editedAlice));
    }

    @Test
    public void toStringFormat_test() {
        Transaction transaction = new TransactionBuilder(TypicalPersons.ALICE).build();
        String msg = "Transaction that is an EARNING:\nDate: " + transaction.getDate() + "\nDescription: "
                + transaction.getDescription()
                + "\nCategory: "
                + transaction.getCategory() + "\nAmount: $" + DECIMAL_FORMAT.format(transaction.getAmount())
                + "\nPaid by: "
                + transaction.getPerson().getName().toString();
        assertEquals(msg, transaction.toString());
    }

    @Test
    public void toStringNegativeFormat_test() {
        Transaction transaction = new TransactionBuilder(TypicalPersons.ALICE).withAmount(-1).build();
        String msg = "Transaction that is a SPENDING:\nDate: " + transaction.getDate() + "\nDescription: "
                + transaction.getDescription()
                + "\nCategory: "
                + transaction.getCategory() + "\nAmount: $"
                + DECIMAL_FORMAT.format(-1.0 * transaction.getAmount())
                + "\nPaid by: "
                + transaction.getPerson().getName().toString();
        assertEquals(msg, transaction.toString());
    }


    @Test
    public void writeIntoFileFormat_test() {
        Transaction transaction = new TransactionBuilder(TypicalPersons.ALICE).build();
        String msg = transaction.getDate() + " | " + transaction.getDescription() + " | " + transaction.getCategory()
                + " | " + transaction.getAmount() + " | " + transaction.getPerson().getName() + " | "
                + transaction.isOne(transaction.getIsReimbursed());
        assertEquals(msg, transaction.toWriteIntoFile());
    }

}
