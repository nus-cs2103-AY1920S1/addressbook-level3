package dukecooks.model.profile.person;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents User's weight.
 */
public class Weight {
    public static final int WEIGHT_LIMIT = 500;

    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain numeric characters and represented by kilograms";

    public static final String MESSAGE_INFLATED_WEIGHT =
            "Weight entered should not exceed " + WEIGHT_LIMIT + "kg! Try again!";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1})?";

    public final double weight;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        AppUtil.checkArgument(isValidNumber(weight), MESSAGE_CONSTRAINTS);
        AppUtil.checkArgument(isValidWeight(Double.parseDouble(weight)), MESSAGE_INFLATED_WEIGHT);
        this.weight = Double.parseDouble(weight);
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if weight is within the limit set.
     * Prevents input of large values set to weight.
     */
    public static boolean isValidWeight(double test) {
        return test > 0 && test <= WEIGHT_LIMIT;
    }

    @Override
    public String toString() {
        return String.valueOf(weight);
    }

}
