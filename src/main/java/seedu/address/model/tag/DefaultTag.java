package seedu.address.model.tag;

import java.util.HashSet;

import seedu.address.model.module.Module;

/**
 * Represents a default Tag.
 */
public class DefaultTag implements Tag {

    private DefaultTagType defaultTagType;
    private HashSet<Module> attachedModules = new HashSet<Module>();

    /**
     * Constructs a {@code DefaultTag}.
     * @param defaultTagType A default tag type in {@code DefaultTagType}.
     */
    public DefaultTag(DefaultTagType defaultTagType) {
        this.defaultTagType = defaultTagType;
    }

    /**
     * Checks if the tag is a default tag.
     * @return True.
     */
    public boolean isDefault() {
        return true;
    }

    /**
     * Returns the default tag type of the current tag.
     * @return The default tag type.
     */
    public DefaultTagType getDefaultTagType() {
        return defaultTagType;
    }

    /**
     * Returns the name of the tag.
     * @return The name of the tag.
     */
    public String getTagName() {
        return defaultTagType.getDefaultTagTypeName();
    }

    /**
     * Returns true if the other tag is also a {@code DefaultTag} and both tags have the same default type.
     * @param other The other {@code Tag}.
     */
    @Override
    public boolean isSameTag(Tag other) {
        if (!other.isDefault()) {
            return false;
        }
        return defaultTagType.equals(((DefaultTag) other).getDefaultTagType());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DefaultTag // instanceof handles nulls
            && defaultTagType.equals(((DefaultTag) other).getDefaultTagType())); // state check
    }

    @Override
    public int hashCode() {
        return defaultTagType.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + getTagName() + ']';
    }

}
