package seedu.address.model;

import java.text.DateFormatSymbols;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import seedu.address.logic.composers.InstantComposer;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a date and time.
 * Internally stored as seconds from epoch, without any timezone information.
 */
@JsonDeserialize(builder = DateTimeBuilder.class)
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

    DateTime(Instant instant) {
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

    public static DateTimeBuilder newBuilder(Instant instant) {
        return new DateTimeBuilder(instant);
    }

    public static DateTimeBuilder newBuilder(long epoch) {
        return new DateTimeBuilder(epoch);
    }

    /**
     * Creates a new instance of DateTime according to the clock.
     *
     * @return A new instance of DateTime according to the clock.
     */
    public static DateTime now() {
        return new DateTime(Instant.now());
    }

    public static DateTime fromIcsString(String string) throws ParseException {
        return ICS_PARSER.parse(string);
    }

    public static DateTime fromDateTimeString(String string) throws ParseException {
        return DATE_TIME_PARSER.parse(string);
    }

    @JsonIgnore
    public Integer getYear() {
        return Integer.valueOf(YEAR_COMPOSER.compose(this.instant));
    }

    @JsonIgnore
    public Integer getMonth() {
        return Integer.valueOf(MONTH_COMPOSER.compose(this.instant));
    }

    @JsonIgnore
    public Integer getWeek() {
        LocalDate localDate = LocalDate.ofInstant(instant, TIME_ZONE);
        return localDate.getDayOfWeek().getValue();
    }

    @JsonIgnore
    public Integer getDay() {
        return Integer.valueOf(DAY_COMPOSER.compose(this.instant));
    }

    @JsonIgnore
    public Integer getHour() {
        return Integer.valueOf(HOUR_COMPOSER.compose(this.instant));
    }

    @JsonIgnore
    public Integer getMinute() {
        return Integer.valueOf(MINUTE_COMPOSER.compose(this.instant));
    }

    @JsonIgnore
    public String getHourString() {
        return HOUR_COMPOSER.compose(this.instant);
    }

    @JsonIgnore
    public String getMinuteString() {
        return MINUTE_COMPOSER.compose(this.instant);
    }

    @JsonIgnore
    public String getEnglishWeekDay() {
        DayOfWeek dayOfWeek = instant.atZone(TIME_ZONE).getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
    }

    @JsonProperty("epoch")
    public long toEpochSeconds() {
        return this.instant.getEpochSecond();
    }

    public Instant toInstant() {
        return this.instant;
    }

    public String toIcsString() {
        return ICS_COMPOSER.compose(this.instant);
    }

    /**
     * Adds a Duration to the current DateTime object to create a new DateTime object representing the
     * Instant of which the Duration has passed.
     * @param duration The Duration object to be added to the DateTime object.
     * @return A DateTime object where the duration has passed.
     */
    public DateTime addDuration(Duration duration) {
        Instant nextInstant = (Instant) duration.addTo(this.instant);
        return new DateTime(nextInstant);
    }

    /**
     * Returns the date and time in readable english for the user.
     *
     * @return The date and time in readable english for the user.
     */
    public String toEnglishDateTime() {
        // TODO: Move to toString()
        String monthStr = new DateFormatSymbols().getMonths()[getMonth() - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return getDay() + " " + monthStr + " " + getYear() + " " + getHourString() + ":" + getMinuteString();
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
