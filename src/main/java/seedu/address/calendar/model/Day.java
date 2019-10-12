package seedu.address.calendar.model;

/**
 * Creates a day object which contains information about the day of month and day of week.
 */
public class Day {
    private DayOfWeek dayOfWeek;
    private int day;

    private Day(DayOfWeek dayOfWeek, int day) {
        this.dayOfWeek = dayOfWeek;
        this.day = day;
    }

    static Day getOneBased(DayOfWeek dayOfWeek, int dayZeroBased) {
        return new Day(dayOfWeek, dayZeroBased + 1);
    }

    int getDayAsNum() {
        return day;
    }

    DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}