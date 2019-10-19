package seedu.address.commons.util;

import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Util class that deals with date-time parsing and display.
 */
public class DateTimeUtil {

    // TODO let user decide display format (currently set to 24hr)
    // TODO let user decide time zone
    public static final String DEFAULT_FORMAT = "dd/MM/yyyy kk:mm";
    public static final String DISPLAY_FORMAT = "EEEE, MMM dd, yyyy HH:mm a";

    public static final String MESSAGE_CONSTRAINTS = "Please follow the " + DEFAULT_FORMAT + " format required";

    public static DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);
    public static DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern(DISPLAY_FORMAT);

    /**
     * Parses the date time given by the user.
      */
    public static LocalDateTime parseDateTime(String rawDateTime) throws ParseException {
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
        return displayFormatter.format(dateTime);
    }

    /**
     * A util method to check if the task is due soon.
     * @param weeks the reminder period specified by the user
     * @param dateTime deadline of the task
     * @return true if task is due within user's reminder period
     */
    public static boolean checkIfDueSoon(long weeks, LocalDateTime dateTime) {
        LocalDateTime dueDate = dateTime.minusWeeks(weeks);
        return LocalDateTime.now().isAfter(dueDate);
    }

}
