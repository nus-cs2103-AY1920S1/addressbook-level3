package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

/**
 * Represents a priority Tag.
 */
public class PriorityTag implements Tag, Comparable<PriorityTag> {

    public static final String MESSAGE_CONSTRAINTS = "Tag names can only be \"high\", \"medium\" or \"low\".";

    private PriorityTagType priorityTagType;

    /**
     * Constructs a {@code PriorityTag}.
     *
     * @param priorityTagType A priority tag type in {@code PriorityTagType}.
     */
    public PriorityTag(PriorityTagType priorityTagType) {
        requireNonNull(priorityTagType);
        this.priorityTagType = priorityTagType;
    }

    /**
     * Checks if the given name is a valid name for a priority tag.
     */
    public static boolean isValidTagName(String name) {
        return PriorityTagType.isValidPriorityTagString(name);
    }

    /**
     * Checks if the tag is a default tag.
     *
     * @return False.
     */
    public boolean isDefault() {
        return false;
    }

    /**
     * Checks if the tag is a priority tag.
     *
     * @return True.
     */
    @Override
    public boolean isPriority() {
        return true;
    }

    /**
     * Returns the priority tag type of the current tag.
     *
     * @return The priority tag type.
     */
    public PriorityTagType getPriorityTagType() {
        return priorityTagType;
    }

    /**
     * Returns the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getTagName() {
        return priorityTagType.getPriorityTagTypeName();
    }

    /**
     * Returns true if the other tag is also a {@code PriorityTag} and both tags have the same priority type.
     *
     * @param other The other {@code Tag}.
     */
    @Override
    public boolean isSameTag(Tag other) {
        if (!other.isPriority()) {
            return false;
        }
        return priorityTagType.equals(((PriorityTag) other).getPriorityTagType());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriorityTag // instanceof handles nulls
                && priorityTagType.equals(((PriorityTag) other).getPriorityTagType())); // state check
    }

    @Override
    public int hashCode() {
        return priorityTagType.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + getTagName() + ']';
    }

    @Override
    public int compareTo(PriorityTag other) {
        return priorityTagType.compareTo(other.priorityTagType);
    }

}
