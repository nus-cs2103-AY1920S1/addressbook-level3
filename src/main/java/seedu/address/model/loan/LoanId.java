package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a loan's ID in LoanRecords.
 * Guarantees: immutable, is valid as declared in {@Link #isValidLoanId(String)}
 */
public class LoanId implements Comparable<LoanId> {

    public static final String MESSAGE_CONSTRAINTS =
            "Loan ID should start with prefix \"L\", followed by 6 digits. They should be unique.";
    public static final String PREFIX = "L";
    public static final String VALIDATION_REGEX = PREFIX + "\\d{6}";
    public final String value;

    /**
     * Constructs a {@code LoanId}
     *
     * @param loanId A valid string of loan ID.
     */
    public LoanId(String loanId) {
        requireNonNull(loanId);
        checkArgument(isValidLoanId(loanId), MESSAGE_CONSTRAINTS);
        value = loanId;
    }

    /**
     * Returns true if the given string is a valid loan ID.
     *
     * @param test String to be tested.
     * @return True if {@code test} is a valid loan ID.
     */
    public static boolean isValidLoanId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int compareTo(LoanId other) {
        return Integer.compare(
                Integer.parseInt(this.toString().substring(1)), Integer.parseInt(other.toString().substring(1)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanId // instanceof handles nulls
                && value.equals(((LoanId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
