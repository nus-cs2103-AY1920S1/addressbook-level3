package seedu.ichifund.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's description in the transaction recorder.
 * Guarantees: immutable; is valid as declared in {@link #isValidLoanDescription(String)}
 */
public class LoanDescription implements Comparable<LoanDescription> {

    public static final String MESSAGE_CONSTRAINTS =
            "LoanDescription should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\A\\pL+\\z]*";

    public final String loandescription;

    /**
     * Constructs a {@code LoanDescription}.
     *
     * @param description A valid Loandescription.
     */
    public LoanDescription(String loandescription) {
        requireNonNull(loandescription);
        checkArgument(isValidLoanDescription(loandescription), MESSAGE_CONSTRAINTS);
        this.loandescription = loandescription;
    }

    /**
     * Returns true if a given string is a valid description for loan.
     */
    public static boolean isValidLoanDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return loandescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanDescription // instanceof handles nulls
                && loandescription.equals(((LoanDescription) other).loandescription)); // state check
    }

    @Override
    public int hashCode() {
        return loandescription.hashCode();
    }

    @Override
    public int compareTo(LoanDescription other) {
        return toString().compareTo(other.toString());
    }
}
