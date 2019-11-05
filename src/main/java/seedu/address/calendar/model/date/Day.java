package seedu.address.calendar.model.date;

import java.util.Arrays;

/**
 * Creates a day object which contains information about the day of month and day of week.
 */
public class Day implements Comparable<Day> {
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

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    Day copy() {
        return new Day(dayOfWeek, dayOfMonth);
    }

    public int compareTo(Day other) {
        return this.dayOfMonth - other.dayOfMonth;
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

    @Override
    public int hashCode() {
        Object[] arr = {dayOfMonth, dayOfWeek};
        return Arrays.hashCode(arr);
    }
}
