package seedu.address.model.performance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *
 */
public class Timing {

    public static final String MESSAGE_CONSTRAINTS = "Timing should only contain numbers in seconds.";
    private static final String REGEX_DECIMAL = "^-?\\d*\\.\\d+$";
    private static final String REGEX_INTEGER = "^-?\\d+$";
    private static final String VALIDATION_REGEX = REGEX_DECIMAL + "|" + REGEX_INTEGER;

    public final double value;

    /**
     * Constructs a {@code Timing}.
     *
     * @param timing A valid timing.
     */
    public Timing(String timing) {
        requireNonNull(timing);
        checkArgument(isValidTiming(timing), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(timing);
    }

    /**
     * Returns true if a given string is a valid timing.
     */
    public static boolean isValidTiming(String test) {
        return test.matches(VALIDATION_REGEX) && (Double.parseDouble(test) > 0);
    }

    public String getUnparsed() {
        return Double.toString(value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " seconds";
    }
}
