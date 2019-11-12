package seedu.address.model.password;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Password's website in the password book.
 */
public class Website {
    public static final String MESSAGE_CONSTRAINTS =
            "Website is optional and could be blank. NIL will be used instead.";

    private static final String VALIDATION_REGEX = ".*";

    public final String value;

    public Website(String website) {
        requireNonNull(website);
        checkArgument(isValidWebsite(website), MESSAGE_CONSTRAINTS);
        value = website;
    }

    /**
     * Returns true if a given string is a valid website.
     */
    public static boolean isValidWebsite(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public boolean isNilWebsite() {
        return value.equals("NIL");
    }

    @Override
    public String toString() {
        if (!value.equals("NIL")) {
            return value;
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Website // instanceof handles nulls
                && value.equals(((Website) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
