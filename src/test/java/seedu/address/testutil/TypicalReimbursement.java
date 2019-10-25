package seedu.address.testutil;

import seedu.address.reimbursement.model.Reimbursement;

public class TypicalReimbursement {
    public static final Reimbursement ALICE_REIMBURSEMENT_20
            = new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_10)
            .withDeadline("01-Dec-2019")
            .build();
    public static final Reimbursement ALICE_REIMBURSEMENT_30
            = new ReimbursementBuilder(TypicalTransactions.ALICE_TRANSACTION_12)
            .withDeadline("01-Nov-2019")
            .build();
    public static final Reimbursement ELLE_REIMBURSEMENT_100
            = new ReimbursementBuilder(TypicalTransactions.ELLE_TRANSACTION_11)
            .build();
}
