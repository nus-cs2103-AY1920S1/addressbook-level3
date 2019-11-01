package cs.f10.t1.nursetraverse.model.datetime;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import cs.f10.t1.nursetraverse.commons.util.AppUtil;

/**
 * Represents a Visit's datetime in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS_BODY = "Date Time should be of the format dd-MM-yyyy HHmm "
            + "and adhere to the following constraints:\n"
            + "1. The values that substitute 'dd', 'MM', 'yyyy', 'HH' and 'mm' must all be numerical numbers.";
    public static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    public static final DateTimeFormatter DATE_PARSER_VALIDATOR = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");

    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid dateTime string.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        AppUtil.checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS_BODY);
        this.dateTime = parseDateTime(dateTime);
    }

    /**
     * Constructs a {@code DateTime}.
     *
     * @param date A valid Date.
     */
    public DateTime(Date date) {
        requireNonNull(date);
        Instant current = date.toInstant();
        LocalDateTime now = LocalDateTime.ofInstant(current, ZoneId.systemDefault());
        this.dateTime = now.truncatedTo(ChronoUnit.MINUTES);
    }

    /**
     * Returns the parsed dateTimeTime if a given string is a valid dateTime; else calls checkArgument
     * which will throw an IllegalArgumentException.
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTime(String value) {
        try {
            return LocalDateTime.parse(value, DATE_PARSER_VALIDATOR);
        } catch (DateTimeParseException e) {
            // This should not happen as we have already validated the value above
            AppUtil.checkArgument(isValidDateTime(value), MESSAGE_CONSTRAINTS_BODY);
            return null;
        }
    }

    /**
     * Returns if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        try {
            DATE_PARSER_VALIDATOR.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Convert date to a string for JSON storage.
     * @return dateTime as a string
     */
    public String toJacksonJsonString() {
        return DATE_PARSER_VALIDATOR.format(dateTime);
    }

    @Override
    public String toString() {
        return DATE_DISPLAY_FORMATTER.format(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

}
