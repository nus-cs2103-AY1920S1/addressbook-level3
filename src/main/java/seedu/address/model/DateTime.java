package seedu.address.model;

import static seedu.address.MainApp.LOCALE;
import static seedu.address.MainApp.TIME_ZONE;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import seedu.address.logic.composers.InstantComposer;
import seedu.address.logic.parser.IcsDateTimeParser;
import seedu.address.logic.parser.UserDateTimeParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a date and time, without seconds.
 * Internally stored as an {@link Instant}, with some helper functions.
 */
@JsonDeserialize(builder = DateTimeBuilder.class)
public class DateTime implements Comparable<DateTime> {

    public static final String USER_DATE_TIME_PATTERN = "dd/MM/uuuu HH:mm";
    public static final String ICS_DATE_TIME_PATTERN = "uuuuMMdd'T'HHmmss'Z'";

    private static final UserDateTimeParser USER_PARSER = new UserDateTimeParser();
    private static final IcsDateTimeParser ICS_PARSER = new IcsDateTimeParser();
    private static final InstantComposer COMPOSER = new InstantComposer();

    // Store as seconds from epoch, no timezone information.
    private final Instant instant;

    DateTime(Instant instant) {
        this.instant = instant;
    }

    public static DateTimeBuilder newBuilder(Instant instant) {
        return new DateTimeBuilder(instant);
    }

    public static DateTimeBuilder newBuilder(int day, int month, int year, int hour, int minute, ZoneId timezone) {
        return new DateTimeBuilder(day, month, year, hour, minute, timezone);
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

    public static DateTime fromUserString(String string) throws ParseException {
        return USER_PARSER.parse(string);
    }

    // TODO: These should be moved to another class because DateTime
    //  has no relation to time zone information. However, doing so will
    //  require a major refactoring of Ui.
    @JsonIgnore
    public Integer getYear() {
        return this.instant.atZone(TIME_ZONE).get(ChronoField.YEAR_OF_ERA);
    }

    @JsonIgnore
    public Integer getMonth() {
        return this.instant.atZone(TIME_ZONE).getMonthValue();
    }

    @JsonIgnore
    public Integer getWeek() {
        return this.instant.atZone(TIME_ZONE).getDayOfWeek().getValue();
    }

    @JsonIgnore
    public Integer getDay() {
        return this.instant.atZone(TIME_ZONE).getDayOfMonth();
    }

    @JsonIgnore
    public Integer getHour() {
        return this.instant.atZone(TIME_ZONE).getHour();
    }

    @JsonIgnore
    public Integer getMinute() {
        return this.instant.atZone(TIME_ZONE).getMinute();
    }

    @JsonIgnore
    public String getEnglishWeekDay() {
        DayOfWeek dayOfWeek = this.instant.atZone(TIME_ZONE).getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);
    }

    @JsonProperty("epoch")
    public long toEpochSecond() {
        return this.instant.getEpochSecond();
    }

    public String toUserString() {
        return COMPOSER.toUserString(this.instant);
    }

    public String toIcsString() {
        return COMPOSER.toIcsString(this.instant);
    }

    /**
     * Adds a Duration to the current DateTime object to create a new DateTime object
     * @param duration The Duration object to be added to the DateTime object.
     * @return A DateTime object where the duration has passed.
     */
    public DateTime plus(Duration duration) {
        return new DateTime(this.instant.plus(duration));
    }

    @Override
    public String toString() {
        return String.format("%02d %s %04d %02d:%02d",
            this.getDay(),
            this.instant.atZone(TIME_ZONE).getMonth().getDisplayName(TextStyle.FULL, LOCALE),
            this.getYear(),
            this.getHour(),
            this.getMinute());
    }

    @Override
    public int compareTo(DateTime o) {
        return this.instant.compareTo(o.instant);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DateTime) {
            DateTime d = (DateTime) object;
            return this.instant.truncatedTo(ChronoUnit.MINUTES)
                .equals(d.instant.truncatedTo(ChronoUnit.MINUTES));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.instant);
    }
}
