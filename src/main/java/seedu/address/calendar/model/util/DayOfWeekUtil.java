package seedu.address.calendar.model.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.calendar.model.date.DayOfWeek;

/**
 * Handles all {@code DayOfWeek} operations and manipulations.
 */
public class DayOfWeekUtil {
    private static final String MESSAGE_CONSTRAINTS = "All days should be represented"
            + " by some value between 0 and 6 inclusive";

    /**
     * Converts numerical ({@code int}) representation of day of week into an instance of {@code DayOfWeek}.
     * 0 represents Sunday, 1 represents Monday, etc.
     * Guarantees: {@code numericalVal} is between 0 and 6 (inclusive)
     *
     * @param numericalVal Numerical representation of the desired day of week
     * @return {@code DayOfWeek} instance which represents the day of week implied by {@code numericalVal}
     */
    static DayOfWeek of(int numericalVal) {
        checkArgument(isValidNumericalVal(numericalVal), MESSAGE_CONSTRAINTS);
        Optional<DayOfWeek> desiredDay = Stream.of(DayOfWeek.values())
                .filter(d -> d.getNumericalVal() == numericalVal)
                .findAny();

        return desiredDay.get();
    }

    /**
     * Checks whether {@code numericalVal} is a valid representation of day of week.
     *
     * @param numericalVal Numerical representation of a day of week
     * @return {@code true} if {@code numericalVal} is between 0 and 6 (inclusive)
     */
    private static boolean isValidNumericalVal(int numericalVal) {
        return numericalVal >= 0 && numericalVal < 7;
    }
}
