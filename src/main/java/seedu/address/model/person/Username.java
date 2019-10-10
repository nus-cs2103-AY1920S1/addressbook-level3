package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's username in the incident management system.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters up to 16 characters, and it should not be blank or contain a whitespace";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]{1,16}";

    public final String value;

    /**
     * Constructs a {@code Username}.
     *
     * @param username A valid name.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_CONSTRAINTS);
        this.value = username;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && value.equals(((Username) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
