package seedu.weme.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in Weme.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    // This is measured so that with the longest tag and \"...\", the meme card will not overflow"
    public static final int TAG_MAX_CHAR = 30;
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric and at most "
            + TAG_MAX_CHAR + " characters.";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     *
     * <p>Limits the length of a tag name to 35 characters to avoid overflow.</p>
     */
    public static boolean isValidTagName(String test) {
        return test.length() <= TAG_MAX_CHAR && test.matches(VALIDATION_REGEX);
    }

    public String getTagName() {
        return tagName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
