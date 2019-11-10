package seedu.sugarmummy.model.time;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date and a time.
 */
public class DateTime implements Comparable<DateTime> {

    public static final String VALIDATION_REGEX = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) "
            + "(2[0-3]|[01][0-9]):[0-5][0-9]$";
    public static final String VALIDATION_PATTERN_STRING = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(VALIDATION_PATTERN_STRING);
    public static final String MESSAGE_CONSTRAINTS = "DateTime should be in the format: yyyy-mm-dd hh:mm and it "
            + "should contain valid number";
    public static final String MESSAGE_FUTURE_CONSTRAINTS = "DateTime should not be in the future";

    private LocalDate date;
    private LocalTime time;

    public DateTime(LocalDate date, LocalTime time) {
        requireNonNull(date);
        requireNonNull(time);
        this.date = date;
        this.time = time;
    }

    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        assert isValidDateTime(dateTime);
        this.date = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER).toLocalDate();
        this.time = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER).toLocalTime();
    }

    public DateTime(LocalDateTime localDateTime) {
        requireNonNull(localDateTime);
        this.date = localDateTime.toLocalDate();
        this.time = localDateTime.toLocalTime();
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        requireNonNull(test);
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        int indexOfSpace = test.indexOf(" ");
        String date = test.substring(0, indexOfSpace);
        String time = test.substring(indexOfSpace + 1);
        return isValidDate(date) && isValidTime(time);
    }

    /**
     * Returns true if a given string is a valid date.
     * Precondition: test passes regex test
     */
    private static boolean isValidDate(String test) {
        String[] dayMonthYear = test.split("-");
        try {
            LocalDate.of(Integer.parseInt(dayMonthYear[0]),
                    Integer.parseInt(dayMonthYear[1]),
                    Integer.parseInt(dayMonthYear[2]));
            return true;
        } catch (NumberFormatException | DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid time.
     * Precondition: test passes regex test
     */
    public static boolean isValidTime(String test) {
        String[] hourMinute = test.split(":");
        try {
            LocalTime.of(
                    Integer.parseInt(hourMinute[0]),
                    Integer.parseInt(hourMinute[1]));
            return true;
        } catch (NumberFormatException | DateTimeException e) {
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

    /**
     * Returns a DateTime with a time duration subtracted.
     */
    public DateTime minus(TimeDuration timeDuration) {
        LocalDateTime current = LocalDateTime.of(date, time);
        return new DateTime(current.minusHours(timeDuration.getHours()).minusMinutes(timeDuration.getMinutes()));
    }

    /**
     * Returns true if time are same.
     *
     * @param dateTime the dateTime to be compared with.
     * @return true if time are same.
     */
    public boolean isSameTime(DateTime dateTime) {
        return this.time.equals(dateTime.getTime());
    }

    /**
     * Returns true if current date time is before the given date time.
     */
    public boolean isBeforeDateTime(DateTime dateTime) {
        return LocalDateTime.of(date, time).isBefore(LocalDateTime.of(dateTime.date, dateTime.time));
    }

    /**
     * Returns true if current date time is after the given date time.
     */
    public boolean isAfterDateTime(DateTime dateTime) {
        return LocalDateTime.of(date, time).isAfter(LocalDateTime.of(dateTime.date, dateTime.time));
    }

    /**
     * Returns true if current time is between the given time.
     */
    public boolean isBetweenTime(DateTime startingDateTime, DateTime endingDateTime) {
        return (time.isAfter(startingDateTime.getTime()) || time.equals(startingDateTime.getTime()))
                && (time.isBefore(endingDateTime.getTime()) || time.equals(endingDateTime.getTime()));
    }

    /**
     * Returns true if current date and time is between the given date and time.
     */
    public boolean isBetweenDateTime(DateTime startingDateTime, DateTime endingDateTime) {
        LocalDateTime thisDateTime = toLocalDateTime();
        LocalDateTime starting = startingDateTime.toLocalDateTime();
        LocalDateTime ending = endingDateTime.toLocalDateTime();
        return (thisDateTime.isAfter(starting) || thisDateTime.equals(starting))
                && (thisDateTime.isBefore(ending) || thisDateTime.equals(ending));
    }

    /**
     * Returns a LocalDateTime object representing the dateTime object.
     */
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.of(date, time);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.of(date, time).format(formatter);
    }

    @Override
    public int compareTo(DateTime dateTime) {
        return toLocalDateTime().compareTo(dateTime.toLocalDateTime());
    }
}
