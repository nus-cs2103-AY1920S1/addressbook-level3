//@@author SakuraBlossom
package seedu.address.model.events.parameters;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Represents a Date Time.
 * Guarantees: Date time is validate and immutable.
 */
public class DateTime implements Comparable<DateTime> {

    public static final String DATETIME_FORMAT = "dd/MM/yy HHmm";
    public static final String MESSAGE_CONSTRAINTS =
            "date time must be valid and follow the format of '" + DATETIME_FORMAT + "'.";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    private final LocalDateTime time;

    public DateTime(LocalDateTime date) {
        requireNonNull(date);
        time = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public static DateTime now() {
        return new DateTime(LocalDateTime.now());
    }

    /**
     * Parses a simple date format as described by {@code DATETIME_FORMAT}, else returns null.
     *
     * @param dateString a simple date format
     * @return a {@code DateTime} if valid, otherwise, returns null.
     */
    public static DateTime tryParseSimpleDateFormat(String dateString) {
        requireNonNull(dateString);
        if (dateString.length() != DATETIME_FORMAT.length()) {
            return null;
        }

        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            return null;
        }

        if (!DATE_FORMATTER.format(localDateTime).equals(dateString)) {
            return null;
        }

        return new DateTime(localDateTime);
    }

    /**
     * Gets new {@code DateTime} object which is {@code years}, {@code months}, {@code days}, {@code weeks},
     * {@code hours} and {@code mins} later from the {@code current} one.
     */
    private static DateTime plusTime(DateTime current, int years, int months,
                                     int weeks, int days, int hours, int minutes) {

        LocalDateTime newLocalDateTime = current.getTime()
                .plusYears(years)
                .plusMonths(months)
                .plusWeeks(weeks)
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes);

        return fromLocalDateTime(newLocalDateTime);
    }

    /**
     * Returns new {@code DateTime} object from a {@code LocalDateTime} object.
     */
    public static DateTime fromLocalDateTime(LocalDateTime localDateTime) {
        return new DateTime(localDateTime.withSecond(0).withNano(0));
    }

    /**
     * gets another DateTime object which is one day later from current one.
     *
     * @param current a given DateTime object
     * @return a {@code DateTime} new object which is one day later from given one.
     */
    public static DateTime plusOneDay(DateTime current) {
        return plusTime(current, 0, 0, 0, 1, 0, 0);
    }

    /**
     * gets another DateTime object which is one week later from current one.
     *
     * @param current a given DateTime object
     * @return a {@code DateTime} new object which is one week later from given one.
     */
    public static DateTime plusOneWeek(DateTime current) {
        return plusTime(current, 0, 0, 1, 0, 0, 0);
    }

    /**
     * gets another DateTime object which is one month later from current one.
     *
     * @param current a given DateTime object
     * @return a {@code DateTime} new object which is one month later from given one.
     */
    public static DateTime plusOneMonth(DateTime current) {
        return plusTime(current, 0, 1, 0, 0, 0, 0);
    }

    /**
     * gets another DateTime object which is one year later from current one.
     *
     * @param current a given DateTime object
     * @return a {@code DateTime} new object which is one year later from given one.
     */
    public static DateTime plusOneYear(DateTime current) {
        return plusTime(current, 1, 0, 0, 0, 0, 0);
    }

    /**
     * gets another DateTime object which is 30 mins later from current one.
     *
     * @param current a given DateTime object
     * @return a {@code DateTime} new object which is one year later from given one.
     */
    public static DateTime plusHalfHour(DateTime current) {
        return plusTime(current, 0, 0, 0, 0, 0, 30);
    }


    public boolean before(DateTime other) {
        return compareTo(other) < 0;
    }

    public boolean beforeOrEqual(DateTime other) {
        return compareTo(other) <= 0;
    }

    @Override
    public int compareTo(DateTime d) {
        requireNonNull(d);
        long deltaSeconds = getTime().until(d.getTime(), ChronoUnit.MINUTES);
        if (deltaSeconds == 0) {
            return 0;
        }
        return (deltaSeconds > 0) ? -1 : 1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherTiming = (DateTime) other;
        return getTime().equals(otherTiming.getTime());
    }


    @Override
    public String toString() {
        return DATE_FORMATTER.format(time);
    }

}
