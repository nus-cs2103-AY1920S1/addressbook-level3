package seedu.guilttrip.commons.core.step;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.util.AppUtil.checkArgument;

/**
 * Step
 */
public class Step {

    public static final long MAX_VALUE = Long.parseLong("9999999");
    public static final String MESSAGE_CONSTRAINTS =
            "Step should only contain an integer. It should also not be bigger than " + MAX_VALUE;
    public static final String VALIDATION_REGEX = "[0-9]+";
    public final long value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param value A valid step value.
     */
    public Step(String value) {
        requireNonNull(value);
        long val = Long.parseLong(value);
        checkArgument(isValidValue(val), MESSAGE_CONSTRAINTS);
        this.value = val;
    }

    /**
     * Returns true if a given string is a valid step.
     */
    public static boolean isValidStep(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
    }

    public static boolean isValidValue(long test) {
        return test <= MAX_VALUE && test > 0;
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Step) // instanceof handles nulls
                && value == ((Step) other).value; // state check
    }

}

