package seedu.planner.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

//@@author oscarsu97

/**
 * Represents the duration of an Activity in the application.
 * Guarantees: immutable;
 */
public class Duration {
    public static final Integer MINUTES_IN_A_DAY = 1440;
    public static final String MESSAGE_CONSTRAINTS =
            "Duration is in minutes and should be a non-zero positive integer less than 1440";
    public static final String VALIDATION_REGEX = "^([1-9][0-9]{0,2}|1[0-4][0-3][0-9])$";

    public final Integer value;

    /**
     * Constructs a {@code Duration}.
     *
     * @param duration A valid duration value.
     */
    public Duration(Integer duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration.toString()), MESSAGE_CONSTRAINTS);
        value = duration;
    }

    /**
     * Returns true if a given integer is a valid duration.
     */
    public static boolean isValidDuration(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && value.equals(((Duration) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
