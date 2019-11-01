package seedu.sugarmummy.model.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import seedu.sugarmummy.model.time.DateTime;

/**
 * Represents a Calendar Entry in the calendar.
 */
public abstract class CalendarEntry implements Comparable<CalendarEntry> {
    private Description description;
    private DateTime dateTime;

    public CalendarEntry(Description description, DateTime dateTime) {
        this.description = description;
        this.dateTime = dateTime;
    }

    public Description getDescription() {
        return description;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public LocalTime getTime() {
        return dateTime.getTime();
    }

    public LocalDate getDate() {
        return dateTime.getDate();
    }

    /**
     * Returns true if both calendar entries are same; This defines a weaker notion of equality between two calendar
     * entries;
     */
    public abstract boolean isSameCalendarEntry(CalendarEntry calendarEntry);

    /**
     * Returns the day of week of the date in the entry.
     *
     * @return the day of week of the date in the entry.
     */
    public DayOfWeek getDayOfWeek() {
        return dateTime.getDayOfWeek();
    }

    /**
     * Returns true if the calendar entry has a date time before the given time.
     */
    public boolean isBefore(DateTime dateTime) {
        return this.dateTime.isBeforeDateTime(dateTime);
    }

    /**
     * Returns true if the calendar entry has a date time between the starting time and ending time.
     */
    public abstract boolean isBetween(DateTime start, DateTime end);

    /**
     * Returns true if the calendar entry is on the given date.
     */
    public boolean isOnDate(LocalDate date) {
        return getDate().equals(date);
    }

    /**
     * Returns a String representation of the day of week of the date.
     *
     * @return a String representation of the day of week of the date.
     */
    public String getDayOfWeekString() {
        return dateTime.getDayOfWeekString();
    }

    @Override
    public int compareTo(CalendarEntry calendarEntry) {
        return this.getTime().compareTo(calendarEntry.getTime());
    }
}
