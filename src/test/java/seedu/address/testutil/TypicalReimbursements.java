package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class TypicalReimbursements {
    public static final Reimbursement ALICE_REIMBURSEMENT_20
            = new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_10)
            .withDeadline("02-Dec-2019")
            .build();

    public static final Reimbursement ALICE_REIMBURSEMENT_30
            = new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_12)
            .withDeadline("01-Nov-2019")
            .build();
    public static final Reimbursement ELLE_REIMBURSEMENT_100
            = new ReimbursementBuilder(TypicalTransactions.ELLE_TRANSACTION_11)
            .build();

    public static ReimbursementList getTypicalReimbursements() {
        ArrayList<Reimbursement> arrList
                = new ArrayList<>(Arrays.asList(ALICE_REIMBURSEMENT_20, ELLE_REIMBURSEMENT_100));
        return new ReimbursementList(arrList);
    }

    public static TransactionList getTypicalTransactions() {
        ArrayList<Transaction> arrayList
                = new ArrayList<>(Arrays.asList(TypicalTransactions.ALICE_TRANSACTION_10,
                TypicalTransactions.ELLE_TRANSACTION_11));
        return new TransactionList(arrayList);
    }

}
