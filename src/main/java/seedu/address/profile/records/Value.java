package seedu.address.profile.records;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents User's input value for the record.
 */
public class Value {
    public static final String MESSAGE_CONSTRAINTS =
            "Value should only contain numeric characters";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final int value;

    /**
     * Constructs a {@code Value}.
     *
     * @param value A valid value.
     */
    public Value(String value) {
        requireNonNull(value);
        checkArgument(isValidNumber(value), MESSAGE_CONSTRAINTS);
        this.value = Integer.parseInt(value);
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

}
