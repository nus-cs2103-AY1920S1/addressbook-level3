package seedu.address.model.events;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a Date Time.
 * Guarantees: Date time is validate and immutable.
 */
public class DateTime implements Comparable<DateTime> {

    public static final String DATETIME_FORMAT = "dd/MM/yy HHmm";
    public static final String MESSAGE_CONSTRAINTS =
            "date time must be follow the format of '" + DATETIME_FORMAT + "'.";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATETIME_FORMAT);
    private final Date time;

    public DateTime(Date date) {
        requireNonNull(date);
        time = date;
    }

    public Date getTime() {
        return time;
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

        try {
            DATE_FORMATTER.setLenient(false);
            Date parseDate = DATE_FORMATTER.parse(dateString);
            return new DateTime(parseDate);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * Gets new {@code DateTime} DateTime object which is {@code years}, {@code months}, {@code days}, {@code weeks},
     * {@code hours} and {@code mins} later from the {@code current} one.
     */
    private static DateTime plusTime(DateTime current, int years, int months,
                                     int weeks, int days, int hours, int minutes) {

        LocalDateTime currentLocalDateTime = LocalDateTime.ofInstant(current.getTime().toInstant(),
                ZoneId.systemDefault());

        LocalDateTime newLocalDateTime = currentLocalDateTime
                .plusYears(years)
                .plusMonths(months)
                .plusWeeks(weeks)
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes);

        Date currentDate = Date.from(newLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return new DateTime(currentDate);
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
        return plusTime(current, 1, 0, 0, 0, 0, 30);
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
        return getTime().compareTo(d.getTime());
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
