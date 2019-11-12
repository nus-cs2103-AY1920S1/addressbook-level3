package seedu.address.model.password;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Password's username in the password book.
 */
public class Username {
    public static final String MESSAGE_CONSTRAINTS =
            "Username should only contain alphabets, numbers, underscores, hyphens, spaces and address signs"
            + " and adhere to the following constraints:\n"
            + "1) Should not have two underscores/hypens/spaces/address signs in a row\n"
            + "2) Should not have a underscore, hypen, space or address sign at the start or end\n"
            + "3) Be between 2 characters to 25 characters long\n";

    private static final String VALIDATION_REGEX = "^(?![@_ .-])(?!.*[ @_.-]{2})([a-zA-Z0-9. _@-]{2,25})(?<![_@ .-])$";


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
                && value.toLowerCase().equals(((Username) other).value.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
