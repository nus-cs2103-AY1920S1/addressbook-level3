package tagline.model.tag;

import java.util.Objects;

/**
 * Tag a contact.
 */
public class HashTag extends Tag {
    public static final String TAG_PREFIX = "#";

    public static final int LENGTH_LIMIT = 30;

    public static final String MESSAGE_CONSTRAINTS = "HashTag at most has 30 characters.";

    private String value;

    /**
     * Constructs a HashTag.
     */
    public HashTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValidValue(String value) {
        return value.length() <= HashTag.LENGTH_LIMIT;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof HashTag) // instanceof handles nulls
                && value.equals(((HashTag) other).value));
    }

    @Override
    public String toString() {
        return TAG_PREFIX + value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
