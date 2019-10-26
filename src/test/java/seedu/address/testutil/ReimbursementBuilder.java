package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.reimbursement.model.Reimbursement;
import seedu.address.transaction.model.Transaction;

public class ReimbursementBuilder {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    public static final LocalDate DEFAULT_DEADLINE = TypicalDeadlines.DEC_DEADLINE;

    private Transaction transaction;
    private LocalDate deadline;


    public ReimbursementBuilder() {
        this.transaction = new TransactionBuilder(TypicalPersons.ALICE)
                .withId(10)
                .withAmount(-20.0)
                .withDate("14-Feb-2019")
                .build();
        this.deadline = DEFAULT_DEADLINE;
    }

    public ReimbursementBuilder(Transaction trans) {
        this.transaction = trans;
        this.deadline = null;
    }

    public ReimbursementBuilder withDeadline(String date) {
        this.deadline = LocalDate.parse(date, DATE_TIME_FORMATTER);
        return this;

    }

    public Reimbursement build() {
        Reimbursement rmb = new Reimbursement(transaction);
        rmb.addDeadline(this.deadline);
        return rmb;
    }


}
