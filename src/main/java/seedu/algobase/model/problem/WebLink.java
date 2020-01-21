package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's weblink in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidWebLink(String)}
 */
public class WebLink implements Comparable<WebLink> {

    public static final String MESSAGE_CONSTRAINTS =
            "Web Links should be legal web URLs.";
    public static final String VALIDATION_REGEX =
            "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)"
            + "?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
    public static final String DEFAULT_WEBLINK_STRING = "";
    public static final WebLink DEFAULT_WEBLINK = new WebLink();

    public final String value;

    /**
     * Constructs an {@code WebLink}.
     *
     * @param weblink A valid weblink.
     */
    public WebLink(String weblink) {
        requireNonNull(weblink);
        checkArgument(isValidWebLink(weblink), MESSAGE_CONSTRAINTS);
        value = weblink;
    }

    private WebLink() {
        value = DEFAULT_WEBLINK_STRING;
    }

    /**
     * Returns true if a given string matches the default weblink string.
     */
    public static boolean isDefaultWebLink(String test) {
        return test.equals(DEFAULT_WEBLINK_STRING);
    }

    /**
     * Returns true if a given {@code WebLink} is a default weblink.
     */
    public static boolean isDefaultWebLink(WebLink test) {
        return test == DEFAULT_WEBLINK;
    }

    /**
     * Returns true if a given string is a valid weblink.
     */
    public static boolean isValidWebLink(String test) {
        return test.matches(VALIDATION_REGEX);
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

    @Override
    public int compareTo(WebLink o) {
        if (this == o) {
            return 0;
        }
        return this.value.compareTo(o.value);
    }
}
