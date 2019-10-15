package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.module.Module;

/**
 * Represents a user-created Tag.
 * Name is valid as declared in {@link #isValidTagName(String)}
 */
public class UserTag implements Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{ASCII}+";

    private String tagName;

    /**
     * Constructs a {@code UserTag}.
     * @param tagName A valid tag name.
     */
    public UserTag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Constructs a {@code UserTag} and attaches a {@code Module}.
     * @param tagName A valid tag name.
     * @param module A valid module.
     */
    public UserTag(String tagName, Module module) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if the tag is a default tag.
     * @return False as user-created tags are not considered default tags.
     */
    public boolean isDefault() {
        return false;
    }

    /**
     * Returns the name of the tag.
     * @return The name of the tag.
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Checks if the tag can be renamed.
     * @return True as user-created tags can be renamed.
     */
    public boolean canBeRenamed() {
        return true;
    }

    /**
     * Renames the tag.
     * @param newName The new name of the tag.
     */
    public void rename(String newName) {
        this.tagName = newName;
    }

    /**
     * Returns true if the other tag is also a {@code UserTag} and the two tags have the same tag name.
     * @param other The other {@code Tag}.
     */
    @Override
    public boolean isSameTag(Tag other) {
        if (other.isDefault()) {
            return false;
        }
        return tagName.equals(((UserTag) other).getTagName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserTag // instanceof handles nulls
                && tagName.equals(((UserTag) other).getTagName())); // state check
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
