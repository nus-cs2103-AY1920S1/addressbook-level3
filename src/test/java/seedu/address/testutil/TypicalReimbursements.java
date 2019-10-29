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
    public static Reimbursement ALICE_REIMBURSEMENT_20 =
            new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_10)
                    .withDeadline("02-Dec-2019")
                    .build();

    public static Reimbursement ALICE_REIMBURSEMENT_30 =
            new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_12)
                    .withDeadline("01-Nov-2019")
                    .build();
    public static Reimbursement ELLE_REIMBURSEMENT_100 =
            new ReimbursementBuilder(TypicalTransactions.ELLE_TRANSACTION_11)
                    .build();

    public static ReimbursementList getTypicalReimbursements() {
        ArrayList<Reimbursement> arrList =
                new ArrayList<>(Arrays.asList(ALICE_REIMBURSEMENT_20, ELLE_REIMBURSEMENT_100));
        return new ReimbursementList(arrList);
    }

    public static TransactionList getTypicalTransactions() {
        ArrayList<Transaction> arrayList =
                new ArrayList<>(Arrays.asList(TypicalTransactions.ALICE_TRANSACTION_10,
                        TypicalTransactions.ELLE_TRANSACTION_11));
        return new TransactionList(arrayList);
    }

    public static void resetReimbursements() {
        ALICE_REIMBURSEMENT_20 =
                new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_10)
                        .withDeadline("02-Dec-2019")
                        .build();

        ALICE_REIMBURSEMENT_30 =
                new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_12)
                        .withDeadline("01-Nov-2019")
                        .build();
        ELLE_REIMBURSEMENT_100 =
                new ReimbursementBuilder(TypicalTransactions.ELLE_TRANSACTION_11)
                        .build();
    }

}
