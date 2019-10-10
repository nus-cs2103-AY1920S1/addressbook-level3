package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's password in the incident management system.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    public static final String MESSAGE_CONSTRAINTS =
            "Passwords should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code Username}.
     *
     * @param password A valid name.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_CONSTRAINTS);
        this.value = password;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && value.equals(((Password) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
