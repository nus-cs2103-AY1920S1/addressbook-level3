package seedu.address.model.loan;

import java.util.stream.IntStream;

import seedu.address.model.LoanRecords;

/**
 * TODO!!
 */
public class LoanIdGenerator {
    public static final int LOAN_ID_LENGTH = 6; // excluding prefix 'L'
    public static final String PREFIX = "L";

    private static int currentLoanIdIndex = 0;

    private static LoanRecords loanRecords;

    public static void setLoanRecords(LoanRecords loanRecords) {
        LoanIdGenerator.loanRecords = loanRecords;
        currentLoanIdIndex = 0;
    }

    /**
     * TODO!!!
     *
     * @return
     */
    public static LoanId generateLoanId() {
        currentLoanIdIndex++;
        // TODO
        return null;
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
}
