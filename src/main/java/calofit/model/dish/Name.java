package calofit.model.dish;

import static java.util.Objects.requireNonNull;

import calofit.commons.util.AppUtil;

/**
 * Represents a Dish's name in the dish database.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String LENGTH_CONSTRAINTS =
            "Names should only be a maximum of 30 characters.";

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
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        AppUtil.checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        AppUtil.checkArgument(isWithinThirtyCharacters(name), LENGTH_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is less than or equal to 30 characters in length.
     * @param test is the string to be tested
     * @return a boolean indicating if the condition is true or false.
     */
    public static boolean isWithinThirtyCharacters(String test) {
        return test.length() <= 30;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

    public String toLowerCase() {
        return this.fullName.toLowerCase();
    }
}
