package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

/**
 * Represents a default Tag.
 */
public class DefaultTag implements Tag {

    private DefaultTagType defaultTagType;

    /**
     * Constructs a {@code DefaultTag}.
     *
     * @param defaultTagType A default tag type in {@code DefaultTagType}.
     */
    public DefaultTag(DefaultTagType defaultTagType) {
        requireNonNull(defaultTagType);
        this.defaultTagType = defaultTagType;
    }

    /**
     * Checks if the tag is a default tag.
     *
     * @return True.
     */
    public boolean isDefault() {
        return true;
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
     * Returns the default tag type of the current tag.
     *
     * @return The default tag type.
     */
    public DefaultTagType getDefaultTagType() {
        return defaultTagType;
    }

    /**
     * Returns the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getTagName() {
        return defaultTagType.getDefaultTagTypeName();
    }

    /**
     * Returns the description of the default tag type.
     */
    public String getDescription() {
        return defaultTagType.getDescription();
    }

    /**
     * Returns true if the other tag is also a {@code DefaultTag} and both tags have the same default type.
     *
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
