package seedu.address.model.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's name in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhoneName(String)}
 */
public class PhoneName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param phoneName A valid name.
     */

    public PhoneName(String phoneName) {
        requireNonNull(phoneName);
        checkArgument(isValidPhoneName(phoneName), MESSAGE_CONSTRAINTS);
        fullName = phoneName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPhoneName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneName // instanceof handles nulls
                && fullName.equals(((PhoneName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
