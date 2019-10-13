package seedu.address.model.loan;

import java.util.stream.IntStream;

import seedu.address.model.LoanRecords;

/**
 * TODO!!
 */
public class LoanIdGenerator {
    public static final int LOAN_ID_LENGTH = 6; // excluding prefix 'L'
    public static final String PREFIX = LoanId.PREFIX;

    private static int currentLoanIdIndex = 0;

    private static LoanRecords loanRecords;

    public static void setLoanRecords(LoanRecords loanRecords) {
        LoanIdGenerator.loanRecords = loanRecords;
        currentLoanIdIndex = 0;
    }

    /**
     * Generates a loan ID based on the current loan ID index.
     *
     * @return A unused {@code LoanId}.
     */
    public static LoanId generateLoanId() {
        currentLoanIdIndex++;
        LoanId id = new LoanId(PREFIX + getPadding() + currentLoanIdIndex);
        while (loanRecords.hasLoan(id)) {
            currentLoanIdIndex++;
            id = new LoanId(PREFIX + getPadding() + currentLoanIdIndex);
        }
        return id;
    }

    private static int getPaddingLength() {
        String stringRepresentation = Integer.toString(currentLoanIdIndex);
        return LOAN_ID_LENGTH - stringRepresentation.length();
    }

    private static String getPadding(int paddingLength) {
        return IntStream.rangeClosed(1, paddingLength)
                .mapToObj(x -> "0")
                .reduce("", (a, b) -> a + b);
    }

    private static String getPadding() {
        return getPadding(getPaddingLength());
    }
}
