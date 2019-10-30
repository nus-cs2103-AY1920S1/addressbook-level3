package seedu.address.model.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a User's colour in the user's biography data. Guarantees: immutable; is valid as declared in {@link
 * #isValidColour(String)}
 */
public class Colour {

    public static final String MESSAGE_CONSTRAINTS =
            "Colour should be a valid colour or a hexadecimal representation of a colour.";

    public static final String HEXADECIMAL_VALIDATION_REGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    private static final List<ColourName> COLOUR_NAMES = Arrays.asList(ColourName.values());
    private static final List<ColourNameAsHexadecimal> COLOUR_NAMES_AS_HEXADECIMAL = Arrays
            .asList(ColourNameAsHexadecimal.values());

    public final String colour;

    /**
     * Constructs a {@code Colour}.
     *
     * @param colour A valid colour.
     */
    public Colour(String colour) {
        requireNonNull(colour);
        checkArgument(isValidColour(colour), MESSAGE_CONSTRAINTS);
        ColourName colourName = getColourNameAsHexadecimal(colour);
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
    private static ColourName getColourNameAsHexadecimal(String hexadecimal) {
        int hexIndex = -1;
        for (int i = 0; i < COLOUR_NAMES_AS_HEXADECIMAL.size(); i++) {
            ColourNameAsHexadecimal colourNameAsHexadecimal = COLOUR_NAMES_AS_HEXADECIMAL.get(i);
            String colourNameAsHexadecimalToString = "#" + colourNameAsHexadecimal.toString().substring(4, 10);
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
     * Returns true if a given string is a valid colour.
     */
    public static boolean isValidColour(String test) {
        if (isHexaDecimal(test)) {
            return true;
        } else {
            for (ColourName colourName : getColourNames()) {
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
    public static boolean isHexaDecimal(String test) {
        return test.matches(HEXADECIMAL_VALIDATION_REGEX);
    }

    public static List<ColourName> getColourNames() {
        return COLOUR_NAMES;
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
