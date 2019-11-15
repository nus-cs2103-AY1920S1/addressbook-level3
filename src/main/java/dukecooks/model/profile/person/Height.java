package dukecooks.model.profile.person;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents User's height in CM.
 */
public class Height {
    public static final double HEIGHT_LIMIT = 300;

    public static final String MESSAGE_CONSTRAINTS =
            "Height should only contain numeric characters and represented by centimeters.";

    public static final String MESSAGE_INFLATED_HEIGHT =
            "Height entered should be within the range of " + HEIGHT_LIMIT + "cm! Try again!";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1})?";

    public final double height;

    /**
     * Constructs a {@code Height}.
     *
     * @param height A valid height.
     */
    public Height(String height) {
        requireNonNull(height);
        AppUtil.checkArgument(isValidNumber(height), MESSAGE_CONSTRAINTS);
        AppUtil.checkArgument(isValidHeight(Double.parseDouble(height)), MESSAGE_INFLATED_HEIGHT);
        this.height = Double.parseDouble(height);
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if height is within the limit set.
     * Prevents input of large values set to height.
     */
    public static boolean isValidHeight(double test) {
        return test > 0 && test <= HEIGHT_LIMIT;
    }

    @Override
    public String toString() {
        return String.valueOf(height);
    }
}
