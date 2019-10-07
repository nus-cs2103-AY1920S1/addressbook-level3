package seedu.address.model.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's colour in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidColour(String)}
 */
public class Colour implements Cloneable {

    public static final String MESSAGE_CONSTRAINTS = "Colours can take any values, and should not be blank";

    /*
     * The first character of the colour must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Colour}.
     *
     * @param colour A valid colour.
     */
    public Colour(String colour) {
        requireNonNull(colour);
        checkArgument(isValidColour(colour), MESSAGE_CONSTRAINTS);
        value = colour;
    }

    /**
     * Returns true if a given string is a valid colour.
     */
    public static boolean isValidColour(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Colour // instanceof handles nulls
                && value.equals(((Colour) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Colour(new String(value));
    }

}
