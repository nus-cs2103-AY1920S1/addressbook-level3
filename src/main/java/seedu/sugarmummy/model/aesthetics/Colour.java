package seedu.sugarmummy.model.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a user's chosen colour for either background or font colour. Guarantees: immutable; is valid as
 * declared in
 * {@link
 * #isValidColour(String)}
 */
public class Colour {

    public static final String MESSAGE_CONSTRAINTS =
            "Colour should be a valid colour or a hexadecimal representation of a colour.";

    private static final String HEXADECIMAL_VALIDATION_REGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private static final int CLOSE_COLOURS_THRESHOLD = 260;

    private static final List<ColourName> COLOUR_NAMES = Arrays.asList(ColourName.values());
    private static final List<ColourNameAsHexadecimal> COLOUR_NAMES_AS_HEXADECIMAL = Arrays
            .asList(ColourNameAsHexadecimal.values());

    private final String hexadecimal;

    private final String colour;

    /**
     * Constructs a {@code Colour}.
     *
     * @param colour A valid colour.
     */
    public Colour(String colour) {
        requireNonNull(colour);
        checkArgument(isValidColour(colour), MESSAGE_CONSTRAINTS);
        if (isHexaDecimal(colour)) {
            hexadecimal = colour;
        } else {
            hexadecimal = getHexaDecimalFromColourName(colour);
        }

        ColourName colourName = getColourNameFromHexadecimal(colour);
        this.colour = (colourName != null)
                ? colourName.toString().toLowerCase()
                : isHexaDecimal(colour)
                        ? colour.toUpperCase()
                        : colour.toLowerCase();
    }

    /**
     * Converts a hexadecimal value entered into a colour name, if available. Otherwise returns null.
     *
     * @param hexadecimal String representation of hexadecimal to be converted.
     * @return Colour name converted from a hexadecimal value if any.
     */
    private static ColourName getColourNameFromHexadecimal(String hexadecimal) {
        int hexIndex = -1;
        for (int i = 0; i < COLOUR_NAMES_AS_HEXADECIMAL.size(); i++) {
            ColourNameAsHexadecimal colourNameAsHexadecimal = COLOUR_NAMES_AS_HEXADECIMAL.get(i);
            String colourNameAsHexadecimalToString = colourNameAsHexadecimal.toString();
            if (colourNameAsHexadecimalToString.equals(hexadecimal.toUpperCase())) {
                hexIndex = i;
                break;
            }
        }
        if (hexIndex != -1) {
            return COLOUR_NAMES.get(hexIndex);
        } else {
            return null;
        }
    }

    /**
     * Converts a colour name entered into a hexadecimal value, if available. Otherwise returns null.
     *
     * @param colour String representation of colour to be converted.
     * @return Colour converted from a hexadecimal value if any.
     */
    private static String getHexaDecimalFromColourName(String colour) {
        int colourIndex = -1;
        for (int i = 0; i < COLOUR_NAMES.size(); i++) {
            ColourName colourName = COLOUR_NAMES.get(i);
            if (colourName.toString().equals(colour.toUpperCase())) {
                colourIndex = i;
                break;
            }
        }
        if (colourIndex != -1) {
            return COLOUR_NAMES_AS_HEXADECIMAL.get(colourIndex).toString();
        } else {
            return null;
        }
    }

    /**
     * Returns true if a given string is a valid colour.
     */
    public static boolean isValidColour(String test) {
        if (isHexaDecimal(test)) {
            return true;
        } else {
            for (ColourName colourName : COLOUR_NAMES) {
                if (colourName.toString().equals(test.toUpperCase())) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns true if a given string is a hexadecimal colour.
     */
    private static boolean isHexaDecimal(String test) {
        return test.matches(HEXADECIMAL_VALIDATION_REGEX);
    }

    /**
     * Returns a value between 0 to 255 representing the levels of red in this Colour.
     */
    private int getRed() {
        return Integer.valueOf(hexadecimal.substring(1, 3), 16);
    }

    /**
     * Returns a value between 0 to 255 representing the levels of green in this Colour.
     */
    private int getGreen() {
        return Integer.valueOf(hexadecimal.substring(3, 5), 16);
    }

    /**
     * Returns a value between 0 to 255 representing the levels of blue in this Colour.
     */
    private int getBlue() {
        return Integer.valueOf(hexadecimal.substring(5, 7), 16);
    }

    /**
     * Returns whether or not this colour is close to the other given colour.
     *
     * @param other Colour to compare to this colour to determine if this colour is close to it.
     * @return Primitive boolean denoting whether or not this colour is close to the other given colour.
     */
    public boolean isCloseTo(Colour other) {
        int red = getRed() - other.getRed();
        int green = getGreen() - other.getGreen();
        int blue = getBlue() - other.getBlue();
        return (red * red + green * green + blue * blue)
                <= CLOSE_COLOURS_THRESHOLD * CLOSE_COLOURS_THRESHOLD;
    }

    @Override
    public String toString() {
        return colour;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Colour // instanceof handles nulls
                && colour.equals(((Colour) other).colour)); // state check
    }

    @Override
    public int hashCode() {
        return colour.hashCode();
    }

}
