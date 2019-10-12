package seedu.address.calendar.model;

/**
 * Creates a day object which contains information about the day of month and day of week.
 */
public class Day {
    private DayOfWeek dayOfWeek;
    private int day;

    Day(DayOfWeek dayOfWeek, int day) {
        this.dayOfWeek = dayOfWeek;
        this.day = day;
    }

    int getDay() {
        return day;
    }

    DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}