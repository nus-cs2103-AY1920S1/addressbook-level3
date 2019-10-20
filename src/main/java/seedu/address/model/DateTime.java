package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.sql.Time;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date and a time.
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime should be in the format: DD-MM-YYYY HH:MM and it " +
            "should contain valid number";
    public static final String VALIDATION_REGEX =
            "^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d\\s+(20|21|22|23|[0-1]?\\d):[0-5]?\\d$";

    private LocalDate date;
    private LocalTime time;

    public DateTime(LocalDate date, LocalTime time) {
        requireNonNull(date);
        requireNonNull(time);
        this.date = date;
        this.time = time;
    }

    public DateTime(LocalDateTime localDateTime) {
        requireNonNull(localDateTime);
        this.date = localDateTime.toLocalDate();
        this.time = localDateTime.toLocalTime();
    }

    private static boolean isValidDate(String test) {
        String[] dmy = test.split("-");
        int day = Integer.parseInt(dmy[0]);
        int month = Integer.parseInt(dmy[1]);
        int year = Integer.parseInt(dmy[2]);
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException exception) {
            return false;
        }
    }

    /**
     * Returns true if the given string contains a valid date and a valid time.
     */
    public static boolean isValidDateTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        String date = test.substring(0, test.indexOf(" "));
        return isValidDate(date);
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

    public DateTime minus(TimeDuration timeDuration) {
        LocalDateTime current = LocalDateTime.of(date, time);
        return new DateTime(current.minusHours(timeDuration.getHours()).minusMinutes(timeDuration.getMinutes()));
    }

    /**
     * Returns true if time are same.
     * @param dateTime the dateTime to be compared with.
     * @return true if time are same.
     */
    public boolean isSameTime(DateTime dateTime) {
        return this.time.equals(dateTime.getTime());
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
