package seedu.address.model.usersettings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the default loan period of a book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLoanPeriod(String)}
 */
public class LoanPeriod {

    private static final int MAX_LOAN_PERIOD = 365;
    public static final String MESSAGE_CONSTRAINTS =
            "Loan period should be a positive non-zero number and should not exceed "
                    + MAX_LOAN_PERIOD + " days.";

    private final int loanPeriod;

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
        requireNonNull(test);
        try {
            int loanPeriod = Integer.parseInt(test);
            return loanPeriod > 0 && loanPeriod <= MAX_LOAN_PERIOD && !test.startsWith("+");
        } catch (NumberFormatException nfe) {
            return false;
        }
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
