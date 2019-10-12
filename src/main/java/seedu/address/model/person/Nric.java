package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's NRIC in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)} (String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS = "Nric must start with a letter 'S/T/F/G', 7 numbers afterward "
            + "and its must end with a letter";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";

    public final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        value = nric.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        return test.toUpperCase().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Nric // instanceof handles nulls
            && value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
