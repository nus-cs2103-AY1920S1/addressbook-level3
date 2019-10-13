package seedu.address.calendar.model;

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
    private Day(DayOfWeek dayOfWeek, int dayOfMonth) {
        // todo add assertion to check validity of dayOfMonth
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Gets a {@code Day} instance such that its day of week is in one-based index.
     * @param dayOfWeek day of the week (e.g. Sunday, Monday, ...)
     * @param dayZeroBased day of the month (e.g. 0, 1, ..., 30)
     */
    static Day getOneBased(DayOfWeek dayOfWeek, int dayZeroBased) {
        return new Day(dayOfWeek, dayZeroBased + 1);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Returns day of week as a meaningful numerical value that is one-based.
     * @return day of week as a meaningful numerical value that is one-based
     */
    public int getDayOfWeekOneBased() {
        return dayOfWeek.getNumericalVal();
    }

    /**
     * Returns day of week as a meaningful numerical value that is zero-based.
     * @return day of week as a meaningful numerical value that is zero-based
     */
    public int getDayOfWeekZeroIndex() {
        return dayOfWeek.getNumericalVal() - 1;
    }
}