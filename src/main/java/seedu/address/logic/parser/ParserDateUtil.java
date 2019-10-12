package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Utility methods for parsing date times and collection of date formats.
 */
public abstract class ParserDateUtil {
    /** The required input date format to use. */
    private static final String DATE_TIME_FORMAT = "d/M/y HHmm";
    /** The required input date format to use. */
    private static final String DATE_FORMAT = "d/M/y";
    /** The required input time format to use. */
    private static final String TIME_FORMAT = "HHmm";
    /** The output format for displaying dates and times. */
    private static final String DISPLAY_FORMAT = "d MMM y h:mma";
    /** The dateTime formatter that uses the DATE_TIME_FORMAT pattern. */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    /** The dateTime formatter that uses the DATE_FORMAT pattern. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    /** The dateTime formatter that uses the TIME_FORMAT pattern. */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    /** The dateTime formatter that uses the DISPLAY_FORMAT pattern. */
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern(DISPLAY_FORMAT);
    /** The error display message format to be shown if parsing fails. */
    private static final String MESSAGE_INVALID_FORMAT = "Invalid %1$s inputted, use %2$s.";

    /**
     * Parses the input string using the {@code DATE_TIME_FORMATTER}.
     * Returns the LocalDateTime representation of the string.
     *
     * @param dateTimeString The input string.
     * @return The localDateTime object.
     * @throws ParseException If the input string format does not follow the pattern.
     */
    public static LocalDateTime getDateTimeFromString(String dateTimeString) throws ParseException {
        try {
            return LocalDateTime.parse(dateTimeString.trim(), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_FORMAT, "date or time", DATE_TIME_FORMAT));
        }
    }

    /**
     * Parses the input string using the {@code DATE_FORMATTER}.
     * Returns the LocalDateTime representation of the string.
     *
     * @param dateString The input string.
     * @return The localDateTime object.
     * @throws ParseException If the input string format does not follow the pattern.
     */
    public static LocalDateTime getDateFromString(String dateString) throws ParseException {
        try {
            return LocalDate.parse(dateString.trim(), DATE_FORMATTER).atStartOfDay();
        } catch (DateTimeParseException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_FORMAT, "date", DATE_FORMAT));
        }
    }

    /**
     * Parses the input string using the timeFormatter.
     * Returns the LocalTime representation of the string.
     *
     * @param timeString The input string.
     * @return The LocalTime object.
     * @throws ParseException If the input string format does not follow the pattern.
     */
    public static LocalTime getTimeFromString(String timeString) throws ParseException {
        try {
            return LocalTime.parse(timeString.trim(), TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_FORMAT, "time", TIME_FORMAT));
        }
    }

    /**
     * Retrieves the string representation of the LocalDateTime object.
     * Uses the displayFormatter and DISPLAY_FORMAT to format the string.
     *
     * @param dateTime The input LocalDateTime object.
     * @return The string representation of the localDateTime.
     */
    public static String getDisplayTime(LocalDateTime dateTime) {
        return DISPLAY_FORMATTER.format(dateTime);
    }

    /**
     * Converts the input {@code localDateTime} using the {@code DATE_FORMATTER}.
     * Returns the date string representation.
     *
     * @param localDateTime The input localDateTime.
     * @return The string representation.
     */
    public static String getStringFromDate(LocalDateTime localDateTime) {
        return DATE_FORMATTER.format(localDateTime);
    }

}
