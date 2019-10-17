package seedu.address.model.usersettings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the default loan period of a book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLoanPeriod(String)}
 */
public class LoanPeriod {

    public static final String MESSAGE_CONSTRAINTS =
            "Loan period should be positive and should not exceed Integer.MAX_VALUE days.";
    public static final String VALIDATION_REGEX = "\\d+";
    public final int loanPeriod;

    /**
     * Constructs a {@code LoanPeriod}.
     *
     * @param loanPeriod A valid loan period in String.
     */
    public LoanPeriod(String loanPeriod) {
        requireNonNull(loanPeriod);
        checkArgument(isValidLoanPeriod(loanPeriod), MESSAGE_CONSTRAINTS);
        this.loanPeriod = Integer.parseInt(loanPeriod);
    }

    /**
     * Constructs a {@code LoanPeriod}.
     *
     * @param loanPeriod A valid loan period in integer.
     */
    public LoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    /**
     * Returns true if a given string is a valid loan period.
     */
    public static boolean isValidLoanPeriod(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%d", loanPeriod);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanPeriod // instanceof handles nulls
                && loanPeriod == (((LoanPeriod) other).loanPeriod)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(loanPeriod);
    }
}
