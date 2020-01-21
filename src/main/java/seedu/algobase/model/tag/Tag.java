package seedu.algobase.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.algobase.model.Id;

/**
 * Represents a Tag in the algobase.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_NAME_CONSTRAINTS =
        "Tags names should contain only alphabets, numbers, hyphen or underscore and should not be empty.";
    public static final String MESSAGE_COLOR_CONSTRAINTS =
        "Tags colors should be one of "
                + "\"RED\", "
                + "\"ORANGE\", "
                + "\"YELLOW\", "
                + "\"GREEN\", "
                + "\"BLUE\", "
                + "\"PURPLE\", "
                + "\"BLACK\", "
                + "\"TEAL\" or "
                + "\"DEFAULT\".";
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9_-]*$";

    public static final String DEFAULT_COLOR = "#3e7b91";
    public final Id id;
    public final String tagName;
    private String tagColor;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument((isValidTagName(tagName) || tagName.equals("#forRefresh#")), MESSAGE_NAME_CONSTRAINTS);
        this.id = Id.generateId();
        this.tagName = tagName;
        this.tagColor = DEFAULT_COLOR;
    }
    public Tag(String tagName, String tagColor) {
        requireAllNonNull(tagName, tagColor);
        checkArgument((isValidTagName(tagName) || tagName.equals("#forRefresh#")), MESSAGE_NAME_CONSTRAINTS);
        checkArgument(isValidTagColor(tagColor), MESSAGE_COLOR_CONSTRAINTS);
        this.id = Id.generateId();
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public Tag(Id id, String tagName) {
        requireAllNonNull(id, tagName);
        checkArgument((isValidTagName(tagName) || tagName.equals("#forRefresh#")), MESSAGE_NAME_CONSTRAINTS);
        this.id = id;
        this.tagName = tagName;
        this.tagColor = DEFAULT_COLOR;
    }
    public Tag(Id id, String tagName, String tagColor) {
        requireAllNonNull(tagName, tagColor);
        checkArgument((isValidTagName(tagName) || tagName.equals("#forRefresh#")), MESSAGE_NAME_CONSTRAINTS);
        checkArgument(isValidTagColor(tagColor), MESSAGE_COLOR_CONSTRAINTS);
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

    public void setColor(String color) {
        this.tagColor = color;
    }
    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     *
     * @param color
     * @return true is a given string is a valid tag color
     */
    public static boolean isValidTagColor(String color) {
        boolean isValid = false;
        if (color.equalsIgnoreCase("RED")
            || color.equalsIgnoreCase("ORANGE")
            || color.equalsIgnoreCase("YELLOW")
            || color.equalsIgnoreCase("GREEN")
            || color.equalsIgnoreCase("BLUE")
            || color.equalsIgnoreCase("PURPLE")
            || color.equalsIgnoreCase("BLACK")
            || color.equalsIgnoreCase("TEAL")
            || color.equals("DEFAULT")
            || color.equalsIgnoreCase("#3e7b91")) {
            isValid = true;
        }
        return isValid;
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
        return '[' + tagName + " " + tagColor + ']';
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
