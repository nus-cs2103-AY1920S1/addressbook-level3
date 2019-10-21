package seedu.address.model.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's gender in the user's biography data.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Colour {

    public static final String MESSAGE_CONSTRAINTS =
            "Colour should be a valid colour or a hexadecimal characters representing RGB";

    /*
     * The first character of the gender must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^$|[\\p{Alnum}][\\p{Alnum} ]*";

    public final String gender;

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender.
     */
    public Colour(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.gender = gender;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return gender;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Colour // instanceof handles nulls
                && gender.equals(((Colour) other).gender)); // state check
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }

}
