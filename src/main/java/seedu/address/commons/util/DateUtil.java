package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing dates with natural language processing.
 */
public class DateUtil {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("E dd MMM yyyy HH:mm:ss z");

    /**
     * Parses a given date in natural language, processes it and returns a formatted date.
     *
     * @param date Input date
     * @return Formatted date as Date type
     * @throws ParseException If the input cannot be parsed
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);

        Parser parser = new Parser();
        List<DateGroup> dateGroups = parser.parse(date);

        if (dateGroups.isEmpty()) {
            throw new ParseException("Invalid input date");
        }

        List<Date> possibleDates = dateGroups.get(0).getDates();

        if (possibleDates.isEmpty()) {
            throw new ParseException("No possible dates from input date");
        }

        return possibleDates.get(0);
    }

    /**
     * Formats a date to a string.
     * Example: Wed 25 Dec 2019 00:00:00 SGT
     *
     * @param date Input date
     * @return A formatted date string
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}
