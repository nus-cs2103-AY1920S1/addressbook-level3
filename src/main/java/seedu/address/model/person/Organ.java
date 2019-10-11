package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's organ in ORGANice.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrgan(String)}
 */
public class Organ {

    public static final String MESSAGE_CONSTRAINTS =
            "Please key in a valid organ. Valid organ(s): kidney.";

    public static final String KIDNEY = "kidney";

    public final String value;

    /**
     * Constructs an {@code Organ}.
     *
     * @param organ A valid organ.
     */
    public Organ(String organ) {
        requireNonNull(organ);
        checkArgument(isValidOrgan(organ), MESSAGE_CONSTRAINTS);
        value = organ.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid organ.
     */
    public static boolean isValidOrgan(String test) {
        return test.toLowerCase().equals(KIDNEY);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Organ // instanceof handles nulls
                && value.equals(((Organ) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
