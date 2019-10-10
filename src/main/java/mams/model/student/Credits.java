package mams.model.student;

import static java.util.Objects.requireNonNull;

import mams.commons.util.AppUtil;

/**
 * Represents a Student's credits number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCredits(String)}
 */
public class Credits {


    public static final String MESSAGE_CONSTRAINTS =
            "Credits numbers should only contain numbers, and it should be at least 2 digits long";
    public static final String VALIDATION_REGEX = "\\d{2,}";
    public final String value;

    /**
     * Constructs a {@code Credits}.
     *
     * @param credits A valid credits number.
     */
    public Credits(String credits) {
        requireNonNull(credits);
        AppUtil.checkArgument(isValidCredits(credits), MESSAGE_CONSTRAINTS);
        value = credits;
    }

    /**
     * Returns true if a given string is a valid credits number.
     */
    public static boolean isValidCredits(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Credits // instanceof handles nulls
                && value.equals(((Credits) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
