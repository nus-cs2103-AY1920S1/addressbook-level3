package seedu.sugarmummy.model.biography;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's NRIC in his / her biography. Guarantees: immutable; is valid as declared in {@link
 * #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
            "NRIC should only contain alphanumeric characters and spaces, and it should not be blank.";

    public static final String VALIDATION_REGEX = "^$|[\\p{Alnum}][\\p{Alnum} ]*";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        this.nric = nric;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                        && nric.equals(((Nric) other).nric)); // state check
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }

}
