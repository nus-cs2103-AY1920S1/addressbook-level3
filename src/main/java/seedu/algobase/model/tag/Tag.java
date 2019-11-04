package seedu.algobase.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.algobase.model.Id;

/**
 * Represents a Tag in the algobase.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS =
        "Tags names should contain only alphabets, numbers, hyphen or underscore and should not be empty.";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_-]*$";

    public static final String DEFAULT_COLOR = "#3e7b91";
    public final Id id;
    public final String tagName;
    public final String tagColor;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.id = Id.generateId();
        this.tagName = tagName;
        this.tagColor = DEFAULT_COLOR;
    }
    public Tag(String tagName, String tagColor) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.id = Id.generateId();
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public Tag(Id id, String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.id = id;
        this.tagName = tagName;
        this.tagColor = DEFAULT_COLOR;
    }
    public Tag(Id id, String tagName, String tagColor) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.id = id;
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public Id getId() {
        return id;
    }

    public String getName() {
        return this.tagName;
    }

    public String getColor() {
        return tagColor;
    }
    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidTagColor(String color) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName.hashCode(), tagColor.hashCode());
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    /**
     * @param otherTag
     * @return whether the current tag the same as otherTag
     */
    public boolean isSameTag(Tag otherTag) {
        if (otherTag == this) {
            return true;
        }

        return otherTag != null
                && otherTag.getName().equals(getName());
    }
}
