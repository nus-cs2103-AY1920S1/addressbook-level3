package seedu.jarvis.model.cca.ccaprogress;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cca's milestone in the CcaMilestoneList.
 * Guarantees: immutable; is valid as declared in {@link #isValidCcaMilestone(String)}
 */
public class CcaMilestone {

    public static final String MESSAGE_CONSTRAINTS =
            "Names must not be blank, otherwise it can contain any character, whitespace or symbol.";

    /*
     * The first character of the ccaMilestone must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Graph}][\\p{Graph} ]*";

    public final String fullName;

    /**
     * Constructs a {@code CcaMilestone}.
     *
     * @param ccaMilestone A valid ccaMilestone.
     */
    public CcaMilestone(String ccaMilestone) {
        requireNonNull(ccaMilestone);
        checkArgument(isValidCcaMilestone(ccaMilestone), MESSAGE_CONSTRAINTS);
        fullName = ccaMilestone;
    }

    /**
     * Returns true if a given string is a valid ccaMilestone.
     */
    public static boolean isValidCcaMilestone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if another {@code CcaMilestone} is the same.
     * @param otherCcaMilestone
     * @return true if another {@code CcaMilestone} is the same.
     */
    public boolean isSameCcaMilestone(CcaMilestone otherCcaMilestone) {
        if (otherCcaMilestone == this) {
            return true;
        }

        return otherCcaMilestone != null
                && otherCcaMilestone.toString().equals(fullName);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaMilestone // instanceof handles nulls
                && fullName.equals(((CcaMilestone) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
