package seedu.address.calendar.model;

import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.util.DayOfWeek;

/**
 * Creates a day object which contains information about the day of month and day of week.
 */
public class Day {
    private DayOfWeek dayOfWeek;
    private int dayOfMonth;

    /**
     * Represents day's day of week in one-based index.
     * @param dayOfWeek day of the week (e.g. Sunday, Monday, ...)
     * @param dayOfMonth day of the month (e.g. 1, 2, ..., 31)
     */
    public Day(DayOfWeek dayOfWeek, int dayOfMonth) {
        // todo add assertion to check validity of dayOfMonth
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Gets a {@code Day} instance such that its day of week is in one-based index.
     * @param dayOfWeek day of the week (e.g. Sunday, Monday, ...)
     * @param dayZeroBased day of the month (e.g. 0, 1, ..., 30)
     */
    public static Day getOneBased(DayOfWeek dayOfWeek, int dayZeroBased) {
        return new Day(dayOfWeek, dayZeroBased + 1);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Returns day of week as a meaningful numerical value that is one-based.
     * @return day of week as a meaningful numerical value that is one-based
     */
    public int getDayOfWeekZeroIndex() {
        return DateUtil.getNumericalVal(dayOfWeek);
    }

    @Override
    public String toString() {
        return String.format("%s, %d", dayOfWeek, dayOfMonth);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Day)) {
            return false;
        }
        Day dayToCompare = (Day) o;
        return dayToCompare.dayOfWeek.equals(this.dayOfWeek) && dayToCompare.dayOfMonth == this.dayOfMonth;
    }
}
