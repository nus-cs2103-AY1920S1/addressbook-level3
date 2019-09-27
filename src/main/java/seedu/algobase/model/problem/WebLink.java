package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a Problem's weblink in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeblink(String)}
 */
public class WebLink {

    public static final String MESSAGE_CONSTRAINTS = "Weblinks should be parsable by java.net.URL";
    public static final String VALIDATION_REGEX =
            "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>";
    public static final WebLink DEFAULT_WEBLINK = new WebLink();

    public final String value;

    /**
     * Constructs an {@code WebLink}.
     *
     * @param weblink A valid weblink.
     */
    public WebLink(String weblink) {
        requireNonNull(weblink);
        checkArgument(isValidWeblink(weblink), MESSAGE_CONSTRAINTS);
        value = weblink;
    }

    private WebLink() {
        value = "nil";
    }

    /**
     * Returns if a given string is a valid weblink.
     */
    public static boolean isValidWeblink(String test) {
        try {
            URL url = new URL(test);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WebLink // instanceof handles nulls
                && value.equals(((WebLink) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
