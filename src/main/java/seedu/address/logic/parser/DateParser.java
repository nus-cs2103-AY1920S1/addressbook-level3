package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses any String object as a Date object.
 * Capable of string conversions to date and Date validation checks.
 */
public class DateParser {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Returns true if given string is a valid date of specified dateFormat
     */
    public static boolean isValidDate(String date) {
        try {
            dateFormat.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if given string is a valid datetime of specified datetimeFormat
     */
    public static boolean isValidDateTime(String datetime) {
        try {
            datetimeFormat.parse(datetime);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return datetimeFormat.format(now);
    }
}
