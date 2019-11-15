package dukecooks.model.health.components;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.util.AppUtil;

/**
 * Represents User's input value for the record.
 */
public class Value {
    public static final String MESSAGE_CONSTRAINTS =
            "Value should only contain numeric characters, rounded by 1 decimal place";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1})?";

    private final double value;

    /**
     * Constructs a {@code Value}.
     *
     * @param value A valid value.
     */
    public Value(String value) {
        requireNonNull(value);
        AppUtil.checkArgument(isValidNumber(value), MESSAGE_CONSTRAINTS);
        this.value = Double.parseDouble(value);
    }

    public double getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid number.
     */
    public static boolean isValidNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Value // instanceof handles nulls
                && value == (((Value) other).value)); // state check
    }

}
