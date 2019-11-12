package io.xpire.model.tag;

import static java.util.Objects.requireNonNull;

import io.xpire.commons.util.AppUtil;

/**
 * Represents a Tag in the expiry date tracker or replenish list. Tags are in Sentence-Case when parsed in.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {
    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric and start with #";
    public static final String EXPIRED_TAG = "Expired";
    private static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final String tagName;
    private boolean isTruncated = false;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        AppUtil.checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        if (tagName.length() > 20) {
            tagName = tagName.substring(0, 20);
            isTruncated = true;
        }
        this.tagName = tagName;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public String getTagName() {
        return this.tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Tag)) {
            return false;
        } else {
            Tag other = (Tag) obj;
            return this.tagName.equals(other.tagName);
        }
    }

    @Override
    public int hashCode() {
        return this.tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "#" + this.tagName;
    }

}
