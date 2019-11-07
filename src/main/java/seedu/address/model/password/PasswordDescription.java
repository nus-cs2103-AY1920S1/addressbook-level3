package seedu.address.model.password;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Password's description in the password book.
 */
public class PasswordDescription {
    public static final String MESSAGE_CONSTRAINTS =
            "Descriptions should only contain alphabets, numbers and spaces"
            + " and adhere to the following constrains: \n"
            + "1) Be between 2 characters to 25 characters long\n";
    public static final String ADDITIONAL_INFORMATION = "Description will automatically be changed to UPPER-CASE";

    /**
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    private static final String VALIDATION_REGEX = "^(?![ ])(?!.*[ ]{2})(([a-zA-Z0-9 ]){2,25})(?<![ ])$";

    public final String value;

    public PasswordDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), (
                MESSAGE_CONSTRAINTS + ADDITIONAL_INFORMATION));
        value = description.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordDescription // instanceof handles nulls
                && value.toUpperCase().equals(((PasswordDescription) other).value.toUpperCase())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
