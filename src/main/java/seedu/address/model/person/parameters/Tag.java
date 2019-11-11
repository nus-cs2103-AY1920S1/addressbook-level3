package seedu.address.model.person.parameters;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should only contain alphanumeric characters";
    private static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private static final HashMap<String, Tag> UNIQUE_UNIVERSAL_TAG_MAP = new HashMap<>();

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    private Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Parses {@code tagName} and issues a instance of {@code Tag}
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Tag issueTag(String tagName) {
        requireNonNull(tagName);
        String trimmedTagName = tagName.trim().toUpperCase();
        Tag storedTag = UNIQUE_UNIVERSAL_TAG_MAP.get(trimmedTagName);
        if (storedTag == null) {
            storedTag = new Tag(trimmedTagName);
            UNIQUE_UNIVERSAL_TAG_MAP.put(trimmedTagName, storedTag);
        }

        return storedTag;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
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
