package seedu.address.model.loan;

import seedu.address.model.LoanRecords;

/**
 * Loan ID generator that generates a {@code LoanId} when a new {@code Loan} is created.
 */
public class LoanIdGenerator {
    public static final int LOAN_ID_LENGTH = 6; // excluding prefix 'L'
    public static final String PREFIX = LoanId.PREFIX;
    public static final String LOAN_ID_FORMATTER = PREFIX + "%0" + LOAN_ID_LENGTH + "d";

    private static LoanRecords loanRecords;

    public static void setLoanRecords(LoanRecords loanRecords) {
        LoanIdGenerator.loanRecords = loanRecords;
    }

    /**
     * Generates a loan ID based on the current loan ID index.
     *
     * @return A unused {@code LoanId}.
     */
    public static LoanId generateLoanId() {
        int nextLoanIdDigits = (loanRecords == null ? 0 : loanRecords.getLoanCount()) + 1;
        return new LoanId(String.format(LOAN_ID_FORMATTER, nextLoanIdDigits));
    }
}
