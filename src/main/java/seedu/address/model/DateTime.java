package seedu.address.model;

import java.text.DateFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import seedu.address.logic.composers.InstantComposer;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a date and time.
 * Internally stored as seconds from epoch, without any timezone information.
 */
public class DateTime implements Comparable<DateTime> {

    public static final String USER_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";

    private static final ZoneId TIME_ZONE = ZoneId.systemDefault();

    // Parsers
    private static final DateTimeParser DATE_TIME_PARSER =
        new DateTimeParser(DateTimeFormatter.ofPattern(USER_DATE_TIME_PATTERN)
            .withZone(TIME_ZONE));

    // Composers
    private static final InstantComposer DATE_TIME_COMPOSER =
        new InstantComposer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            .withZone(TIME_ZONE));
    private static final InstantComposer DAY_COMPOSER =
            new InstantComposer(DateTimeFormatter.ofPattern("dd")
                    .withZone(TIME_ZONE));
    private static final InstantComposer MONTH_COMPOSER =
            new InstantComposer(DateTimeFormatter.ofPattern("MM")
                    .withZone(TIME_ZONE));
    private static final InstantComposer YEAR_COMPOSER =
            new InstantComposer(DateTimeFormatter.ofPattern("yyyy")
                    .withZone(TIME_ZONE));
    private static final InstantComposer HOUR_COMPOSER =
            new InstantComposer(DateTimeFormatter.ofPattern("HH")
                    .withZone(TIME_ZONE));
    private static final InstantComposer MINUTE_COMPOSER =
            new InstantComposer(DateTimeFormatter.ofPattern("mm")
                    .withZone(TIME_ZONE));

    private static final DateTimeFormatter ICS_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
        .withZone(ZoneId.of("UTC"));
    private static final DateTimeParser ICS_PARSER = new DateTimeParser(ICS_FORMATTER);
    private static final InstantComposer ICS_COMPOSER = new InstantComposer(ICS_FORMATTER);

    private final Instant instant;

    public DateTime(Instant instant) {
        this.instant = instant;
    }

    public DateTime(LocalDate localDate) {
        // No need to care about time
        this.instant = localDate.atStartOfDay(TIME_ZONE).toInstant();
    }

    public DateTime(YearMonth yearMonth) {
        // No need to care about day or time. Only month and year.
        this.instant = yearMonth.atEndOfMonth().atStartOfDay(TIME_ZONE).toInstant();
    }

    public static DateTime fromIcsString(String string) throws ParseException {
        return ICS_PARSER.parse(string);
    }

    public static DateTime fromDateTimeString(String string) throws ParseException {
        return DATE_TIME_PARSER.parse(string);
    }

    public Integer getDay() {
        return Integer.valueOf(DAY_COMPOSER.compose(this.instant));
    }

    public Integer getWeek() {
        LocalDate localDate = LocalDate.ofInstant(instant, TIME_ZONE);
        return localDate.getDayOfWeek().getValue();
    }

    public Integer getMonth() {
        return Integer.valueOf(MONTH_COMPOSER.compose(this.instant));
    }

    public Integer getYear() {
        return Integer.valueOf(YEAR_COMPOSER.compose(this.instant));
    }

    public Integer getHour() {
        return Integer.valueOf(HOUR_COMPOSER.compose(this.instant));
    }

    public Integer getMinute() {
        return Integer.valueOf(MINUTE_COMPOSER.compose(this.instant));
    }

    /**
     * Creates a new instance of DateTime according to the clock.
     *
     * @return A new instance of DateTime according to the clock.
     */
    public static DateTime now() {
        return new DateTime(Instant.now());
    }

    /**
     * Computes the number of milliseconds between the calling instance of DateTime
     *     and the argument instance of Time. If the argument instance of DateTime
     *     occurs before the calling instance, the result returned will be negative.
     *
     * @param futureTime A DateTime that does not have (but is expected) to be in the future.
     * @return The number of milliseconds between the calling instance of DateTime
     *     and the argument instance of DateTime.
     */
    public long msecsTimeUntil(DateTime futureTime) {
        return futureTime.instant.toEpochMilli() - this.instant.toEpochMilli();
    }

    public Instant toInstant() {
        return this.instant;
    }

    public String toIcsString() {
        return ICS_COMPOSER.compose(this.instant);
    }

    /**
     * Returns the date and time in readable english for the user.
     *
     * @return The date and time in readable english for the user.
     */
    public String toEnglishDateTime() {
        String monthStr = new DateFormatSymbols().getMonths()[getMonth() - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return getDay() + " " + monthStr + " " + getYear() + " " + getHour() + ":" + getMinute();
    }

    @Override
    public String toString() {
        return DATE_TIME_COMPOSER.compose(this.instant);
    }

    @Override
    public int compareTo(DateTime o) {
        return this.instant.compareTo(o.instant);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DateTime) {
            DateTime d = (DateTime) object;
            return this.instant.equals(d.instant);
        }
        return false;
    }

    /**
     * Checks if two instances of DateTime are equal up to the current minute.
     *
     * @param other The DateTime to be compared to
     * @return <code> true </code> only if both this instance and the other instance of DateTime are equal
     *     up to the current minute, but not any more precise than that.
     */
    public boolean equalsPrecisionMinute(DateTime other) {
        return this.instant.truncatedTo(ChronoUnit.MINUTES)
                .equals(other.instant.truncatedTo(ChronoUnit.MINUTES));
    }
}
