package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Reimbursements} objects to be used in tests.
 */
public class TypicalReimbursements {

    private Reimbursement aliceReimbursement20;
    private Reimbursement aliceReimbursement30;
    private Reimbursement elleReimbursement100;

    private Transaction aliceTransaction10;
    private Transaction elleTransaction11;
    private Transaction aliceTransaction12;
    private Transaction bobTransaction13;

    public TypicalReimbursements() {
        resetReimbursements();
    }

    public Reimbursement getAliceReimbursement20() {
        return aliceReimbursement20;
    }

    public Reimbursement getAliceReimbursement30() {
        return aliceReimbursement30;
    }

    public Reimbursement getElleReimbursement100() {
        return elleReimbursement100;
    }

    public ReimbursementList getTypicalReimbursements() {
        ArrayList<Reimbursement> arrList =
                new ArrayList<>(Arrays.asList(aliceReimbursement20, elleReimbursement100));
        return new ReimbursementList(arrList);
    }

    public Transaction getAliceTransaction10() {
        return aliceTransaction10;
    }

    public Transaction getAliceTransaction12() {
        return aliceTransaction12;
    }

    public Transaction getBobTransaction13() {
        return bobTransaction13;
    }

    public Transaction getElleTransaction11() {
        return elleTransaction11;
    }

    public TransactionList getTypicalTransactions() {
        ArrayList<Transaction> arrayList =
                new ArrayList<>(Arrays.asList(aliceTransaction10,
                        elleTransaction11));
        return new TransactionList(arrayList);
    }

    /**
     * Resets typical reimbursements in case other tests modify them.
     */
    public void resetReimbursements() {
        aliceTransaction10 = new TransactionBuilder(TypicalPersons.ALICE)
                .withId(10)
                .withAmount(-20.00)
                .withDate("14-Feb-2019")
                .build();
        elleTransaction11 = new TransactionBuilder(TypicalPersons.ELLE)
                .withId(11)
                .withAmount(-100.00)
                .withDescription("food")
                .withDate("12-Jun-2019")
                .build();
        aliceTransaction12 = new TransactionBuilder(TypicalPersons.ALICE)
                .withId(12)
                .withAmount(-30.00)
                .withDate("14-Oct-2019")
                .build();
        bobTransaction13 = new TransactionBuilder(TypicalPersons.BOB)
                .withId(13)
                .withAmount(-50.00)
                .withDate("26-Oct-2019")
                .build();

        aliceReimbursement20 =
                new ReimbursementBuilder(aliceTransaction10)
                        .withDeadline("02-Dec-2019")
                        .build();

        aliceReimbursement30 =
                new ReimbursementBuilder(aliceTransaction12)
                        .withDeadline("01-Nov-2019")
                        .build();
        elleReimbursement100 =
                new ReimbursementBuilder(elleTransaction11)
                        .build();
    }

}
