package seedu.address.model.password;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Password's username in the password book.
 */
public class Username {
    public static final String MESSAGE_CONSTRAINTS =
            "Username should only contain alphanumeric characters, underscore, hyphen and spaces"
            + "and adhere to the following constraints:\n"
            + "1) Should not have two underscores, two hypens or two spaces in a row\n"
            + "2) Should not have a underscore, hypen or space at the start or end\n"
            + "3) Should not be empty \n"
            + "4) Be at least 2 characters long";
    //TODO: Check regex if its correct.
    //private static final String VALIDATION_REGEX = "^(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    private static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_CONSTRAINTS);
        value = username;
    }

    /**
     * Returns true if a given string is a valid username.
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
