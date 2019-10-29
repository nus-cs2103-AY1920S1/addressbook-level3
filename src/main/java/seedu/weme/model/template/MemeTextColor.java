package seedu.weme.model.template;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.AppUtil.checkArgument;

import java.awt.Color;
import java.util.Optional;

/**
 * Represents a {@code MemeText}'s color.
 * Guarantees: immutable; is valid as declared in {@link #isValidMemeTextColor}}
 */
public class MemeTextColor {

    public static final String MESSAGE_CONSTRAINTS = "Color must be either a valid color name, or a HEX string"
        + "denoting the RGB values";
    public static final String DEFAULT_MEME_TEXT_COLOR = "#FFFFFF";

    private final Color color;

    /**
     * Constructs a {@code MemeTextColor}.
     *
     * @param color A valid meme text color
     */
    public MemeTextColor(String color) {
        requireNonNull(color);
        checkArgument(isValidMemeTextColor(color), MESSAGE_CONSTRAINTS);
        this.color = getColorByName(color).orElseGet(() -> getColorByHex(color).get());
    }

    /**
     * Returns true if a given string is a valid meme text color.
     */
    public static boolean isValidMemeTextColor(String test) {
        return getColorByName(test).isPresent() || getColorByHex(test).isPresent();
    }

    private static Optional<Color> getColorByHex(String hex) {
        try {
            return Optional.of(Color.decode(hex));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static Optional<Color> getColorByName(String name) {
        try {
            return Optional.of((Color) Color.class.getField(name.toUpperCase()).get(null));
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            return Optional.empty();
        }
    }

    /**
     * Returns the underlying {@link java.awt.Color}.
     * @return the underlying {@link java.awt.Color}
     */
    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MemeTextColor // instanceof handles nulls
            && color.equals(((MemeTextColor) other).color)); // state check
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

}
