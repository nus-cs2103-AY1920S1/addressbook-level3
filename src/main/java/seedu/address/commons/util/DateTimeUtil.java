package seedu.address.commons.util;

import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.requireNonNull;

/**
 * Util class that deals with date-time parsing and display.
 */
public class DateTimeUtil {

    public static final String DEFAULT_INPUT_FORMAT = "dd-MM-yyyy kk:mm";
    public static final String DISPLAY_FORMAT_TWENTY_FOUR_HOUR = "EEEE, MMM dd, yyyy HH:mm";
    public static final String DISPLAY_FORMAT_TWELVE_HOUR = "EEEE, MMM dd, yyyy hh:mm a";

    public static final String MESSAGE_CONSTRAINTS = "Please follow the " + DEFAULT_INPUT_FORMAT + " format required";

    private static DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(DEFAULT_INPUT_FORMAT);
    private static DateTimeFormatter displayFormatterTwentyFourHour =
            DateTimeFormatter.ofPattern(DISPLAY_FORMAT_TWENTY_FOUR_HOUR);
    private static DateTimeFormatter displayFormatterTwelveHour =
            DateTimeFormatter.ofPattern(DISPLAY_FORMAT_TWELVE_HOUR);
    private static DateTimeFormatter defaultDisplayFormat = displayFormatterTwentyFourHour;

    public static DateTimeFormatter getDisplayFormatterTwentyFourHour() {
        return displayFormatterTwentyFourHour;
    }

    public static DateTimeFormatter getDisplayFormatterTwelveHour() {
        return displayFormatterTwelveHour;
    }

    public static DateTimeFormatter getDefaultFormatter() {
        return defaultFormatter;
    }

    /**
     * Switches the default clock display format of +Work with {@code newFormat}.
     */
    public static void switchDisplayFormat(DateTimeFormatter newFormat) {
        defaultDisplayFormat = newFormat;
    }

    /**
     * Parses the date time given by the user.
      */
    public static LocalDateTime parseDateTime(String rawDateTime) throws ParseException {
        requireNonNull(rawDateTime);
        try {
            return LocalDateTime.parse(rawDateTime, defaultFormatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Display the date/time in a user friendly-manner
     */
    public static String displayDateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        return defaultDisplayFormat.format(dateTime);
    }

    /**
     * Checks if a task is due soon by comparing its due date to the current date and time.
     * @param weeks the reminder period specified by the user
     * @param dateTime deadline of the task
     * @return true if task is due within user's reminder period
     */
    public static boolean checkIfDueSoon(int weeks, LocalDateTime dateTime) {
        requireNonNull(dateTime);
        LocalDateTime dueDate = dateTime.minusWeeks(weeks);
        return LocalDateTime.now().isAfter(dueDate);
    }

    public static DateTimeFormatter getDefaultDisplayFormat() {
        return defaultDisplayFormat;
    }
}
