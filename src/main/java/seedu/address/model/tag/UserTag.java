package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.tag.exceptions.InvalidTagNameException;

/**
 * Represents a user-created Tag.
 * Name is valid as declared in {@link #isValidTagName(String)}
 */
public class UserTag implements Tag {
    public static final String MESSAGE_CONSTRAINTS = "Tag names should be alphanumeric and not be default tag names";
    public static final String VALIDATION_REGEX = "\\p{ASCII}+";

    private String tagName;

    /**
     * Constructs a {@code UserTag}.
     *
     * @param tagName A valid tag name.
     */
    public UserTag(String tagName) {
        requireNonNull(tagName);
        try {
            checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        } catch (IllegalArgumentException exception) {
            throw new InvalidTagNameException(exception.getMessage());
        }
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX) && (!DefaultTagType.contains(test));
    }

    /**
     * Checks if the tag is a default tag.
     *
     * @return False as user-created tags are not considered default tags.
     */
    public boolean isDefault() {
        return false;
    }

    /**
     * Checks if the tag is a priority tag.
     *
     * @return False.
     */
    public boolean isPriority() {
        return false;
    }

    /**
     * Returns the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Renames the tag.
     *
     * @param newName The new name of the tag.
     */
    public void rename(String newName) throws InvalidTagNameException {
        if (DefaultTagType.contains(newName)) {
            throw new InvalidTagNameException("Tags cannot be renamed to default tag names");
        }
        this.tagName = newName;
    }

    /**
     * Returns true if the other tag is also a {@code UserTag} and the two tags have the same tag name.
     *
     * @param other The other {@code Tag}.
     */
    @Override
    public boolean isSameTag(Tag other) {
        if (other.isDefault()) {
            return false;
        }
        return tagName.compareToIgnoreCase(other.getTagName()) == 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserTag // instanceof handles nulls
                && (tagName.compareToIgnoreCase(((UserTag) other).getTagName()) == 0)); // state check
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
