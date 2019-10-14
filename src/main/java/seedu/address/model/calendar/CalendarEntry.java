package seedu.address.model.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a Calendar Entry in the calendar.
 */
public abstract class CalendarEntry {
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
     * Returns the day of week of the date in the entry.
     * @return the day of week of the date in the entry.
     */
    public DayOfWeek getDayOfWeek() {
        return dateTime.getDayOfWeek();
    }

    /**
     * Returns a String representation of the day of week of the date.
     * @return a String representation of the day of week of the date.
     */
    public String getDayOfWeekString() {
        return dateTime.getDayOfWeekString();
    }
}
