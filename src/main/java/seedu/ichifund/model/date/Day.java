package seedu.ichifund.model.date;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.util.AppUtil.checkArgument;

public class Day {
    public static final String MESSAGE_CONSTRAINTS =
            "Day should only contain numbers, from 1 to 31";
    public static final String VALIDATION_REGEX = "[1-9]|[1-2]\\d|3[0-1]";
    public final int dayNumber;

    /**
     * Constructs a {@code Day}.
     *
     * @param day A valid day number.
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_CONSTRAINTS);
        dayNumber = Integer.parseInt(day);
    }

    /**
     * Returns true if a given string is a valid day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "" + dayNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && dayNumber == (((Day) other).dayNumber)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

