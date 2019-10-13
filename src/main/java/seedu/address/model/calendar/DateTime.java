package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date and a time.
 */
public class DateTime {

    //TODO: constraint
    public static final String MESSAGE_CONSTRAINTS = "some constraint date time";

    private LocalDate date;
    private LocalTime time;

    public DateTime(LocalDate date, LocalTime time) {
        requireNonNull(date);
        requireNonNull(time);
        this.date = date;
        this.time = time;
    }

    //TODO: regex for date time
    public static boolean isValidDateTime(String test) {
        return true;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public DayOfWeek getDayOfWeek() {
        return date.getDayOfWeek();
    }

    public String getDayOfWeekString() {
        String dayString = getDayOfWeek().toString();
        return dayString.substring(0, 1).concat(dayString.substring(1).toLowerCase());
    }

    /**
     * Returns true if both date and time are same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return LocalDateTime.of(date, time).equals(LocalDateTime.of(otherDateTime.date, otherDateTime.time));
    }

    @Override
    public int hashCode() {
        return LocalDateTime.of(date, time).hashCode();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm");
        return LocalDateTime.of(date, time).format(formatter);
    }
}
