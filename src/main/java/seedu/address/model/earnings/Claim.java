package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tutor's earnings status.
 * Guarantees: immutable; is valid as declared in {@link #isValidClaim(String)}
 */
public class Claim {

    public static final String MESSAGE_CONSTRAINTS =
            "Claim should only contain the valid input "
                    + "(i.e. processing/approved/rejected/pending submission), and it should not be blank.";

    public static final String VALIDATION_REGEX = "approved|processing|rejected|pending submission";

    public final String claimStatus;

    /**
     * Constructs a {@code Claim}.
     *
     * @param status A valid claim string.
     */
    public Claim(String status) {
        requireNonNull(status);
        checkArgument(isValidClaim(status), MESSAGE_CONSTRAINTS);
        claimStatus = status;
    }

    /**
     * Returns true if a given string is a valid claim.
     */
    public static boolean isValidClaim(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return claimStatus;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Claim // instanceof handles nulls
                && claimStatus.equals(((Claim) other).claimStatus)); // state check
    }

    @Override
    public int hashCode() {
        return claimStatus.hashCode();
    }

}
