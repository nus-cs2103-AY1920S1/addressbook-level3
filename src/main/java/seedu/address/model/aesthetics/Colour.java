package seedu.address.model.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a User's colour in the user's biography data.
 * Guarantees: immutable; is valid as declared in {@link #isValidColour(String)}
 */
public class Colour {

    public static final String MESSAGE_CONSTRAINTS =
            "Colour should be a valid colour or a hexadecimal representation of a colour.";

    private static final List<ColourName> COLOUR_NAMES = Arrays.asList(ColourName.values());

    /*
     * The first character of the colour must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String HEXADECIMAL_VALIDATION_REGEX = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    public final String colour;

    /**
     * Constructs a {@code Colour}.
     *
     * @param colour A valid colour.
     */
    public Colour(String colour) {
        requireNonNull(colour);
        checkArgument(isValidColour(colour), MESSAGE_CONSTRAINTS);
        this.colour = colour;
    }

    /**
     * Returns true if a given string is a valid colour.
     */
    public static boolean isValidColour(String test) {
        if (test.matches(HEXADECIMAL_VALIDATION_REGEX)) {
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

    public static List<ColourName> getColourNames() {
        return COLOUR_NAMES;
    }

    @Override
    public int hashCode() {
        return colour.hashCode();
    }

}
