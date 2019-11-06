package seedu.weme.model.template;

import static seedu.weme.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a {@code MemeText}'s font size.
 * 6 sizes are possible, with 1 being the smallest and 6 being the largest.
 * Guarantees: immutable; is valid as declared in {@link #isValidFontSize}}
 */
public class MemeTextSize {

    public static final String MESSAGE_CONSTRAINTS = "Font size must be an integer from 1 to 6";
    public static final MemeTextSize DEFAULT_MEME_TEXT_SIZE = new MemeTextSize("3");

    private int size;

    /**
     * Constructs a {@code MemeTextSize}.
     *
     * @param color A valid meme text color
     */
    public MemeTextSize(String size) {
        checkArgument(isValidMemeTextSize(size), MESSAGE_CONSTRAINTS);
        this.size = Integer.parseInt(size);
    }

    /**
     * Returns true if a given string is a valid meme text size.
     */
    public static boolean isValidMemeTextSize(String test) {
        try {
            int s = Integer.parseInt(test);
            return 1 <= s && s <= 6;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getSize() {
        return (size - 1) * 20 + 50;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MemeTextSize // instanceof handles nulls
            && size == ((MemeTextSize) other).size); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

}
