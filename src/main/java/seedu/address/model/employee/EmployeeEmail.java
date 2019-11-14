package seedu.address.model.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Employee's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class EmployeeEmail {

    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain.com "
            + "and adhere to the following constraints:\n"
            + "1. For both the local-part and domain-part, it may contain dots but "
            + "no two dots can appear next to each other. "
            + "The first and last characters must not be dots. \n"
            + "2. The local-part should only contain alphanumeric characters and the "
            + "special characters dot (.) , dash (-), and underscore (_).\n"
            + "3. This is followed by a '@' and then a domain name. "
            + "The domain name must:\n"
            + "    - consist of alphanumeric characters, a period or a hyphen for the characters in between, if any. \n"
            + "    - contain at least one dot (.) \n"
            + "    - the top-level (e.g. .com) must be at least 2 characters and at most 6 characters long \n"
            + "    - start and end with alphanumeric characters\n";

    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public final String value;

    /**
     * Constructs an {@code EmployeeEmail}.
     *
     * @param email A valid email address.
     */
    public EmployeeEmail(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeEmail // instanceof handles nulls
                && value.equals(((EmployeeEmail) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
