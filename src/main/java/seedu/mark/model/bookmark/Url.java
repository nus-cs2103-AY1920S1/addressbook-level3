package seedu.mark.model.bookmark;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.AppUtil.checkArgument;

/**
 * Represents a Bookmark's URL in Mark.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Url {

    private static final String SPECIAL_CHARACTERS = "\\(\\.\\-_~!\\$&'\\*\\+,;=:@\\)";
    private static final String NON_PERIOD_SPECIAL_CHARACTERS = "\\(\\-_~!\\$&'\\*\\+,;=:@\\)";
    public static final String MESSAGE_CONSTRAINTS = "URLs should be of the format "
            + "scheme://authority[/path][?query][#fragment][/] "
            + "and adhere to the following constraints:\n"
            + "1. The scheme should be either 'http', 'https', 'ftp', or 'file', followed by '://'.\n"
            + "2. The authority should only contain alphanumeric characters and these special characters, including "
            + "the parentheses, " + SPECIAL_CHARACTERS + " . "
            + "In addition, the authority must:\n"
            + "    - be at least 2 characters long\n"
            + "    - not start or end with a period\n"
            + "3. This can be followed by zero or more path segments. "
            + "Each path segment begins with a '/' followed by one or more alphanumeric or special characters.\n"
            + "4. Next, a URL may contain a query string, which begins with a '?'.\n"
            + "5. It may also contain a fragment after the query string (if present), which begins with a '#'.\n"
            + "6. Finally, a URL can end with an optional slash '/'.\n";
    private static final String REGEX_URL_SCHEME = "^((http(s?))|(ftp)|(file))://";
    // alphanumeric and special characters
    private static final String URL_CHARACTERS = "[\\w" + SPECIAL_CHARACTERS + "]";
    // alphanumeric and special characters excluding period '.'
    private static final String NON_PERIOD_URL_CHARACTERS = "[\\w" + NON_PERIOD_SPECIAL_CHARACTERS + "]";
    private static final String REGEX_URL_AUTHORITY =
            NON_PERIOD_URL_CHARACTERS + URL_CHARACTERS + "*" + NON_PERIOD_URL_CHARACTERS;
    private static final String REGEX_URL_PATH_SEGMENT = "/" + URL_CHARACTERS + "+";
    private static final String REGEX_URL_PATH = "(" + REGEX_URL_PATH_SEGMENT + ")*"; // zero or more path segments
    private static final String REGEX_URL_QUERY = "(\\?" + URL_CHARACTERS + "+)?"; // optional query
    private static final String REGEX_URL_FRAGMENT = "(#" + URL_CHARACTERS + "+)?"; // optional fragment
    public static final String VALIDATION_REGEX = REGEX_URL_SCHEME + REGEX_URL_AUTHORITY
            + REGEX_URL_PATH + REGEX_URL_QUERY + REGEX_URL_FRAGMENT + "/?$";

    public final String value;

    /**
     * Constructs a {@code Url}.
     *
     * @param email A valid URL.
     */
    public Url(String email) {
        requireNonNull(email);
        checkArgument(isValidUrl(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a URL.
     */
    public static boolean isValidUrl(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Url // instanceof handles nulls
                && value.equals(((Url) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
