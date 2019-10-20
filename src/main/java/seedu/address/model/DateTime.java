package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public static final String VALIDATION_REGEX_STRING = "yyyy-MM-dd HH:mm";
    public static final SimpleDateFormat VALIDATION_REGEX = new SimpleDateFormat(VALIDATION_REGEX_STRING);

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

    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        if (isValidDateTime(dateTime)) {
            this.date = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern(VALIDATION_REGEX_STRING));
            this.time = LocalTime.parse(dateTime, DateTimeFormatter.ofPattern(VALIDATION_REGEX_STRING));
        } else {
        }
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        try {
            VALIDATION_REGEX.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
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
