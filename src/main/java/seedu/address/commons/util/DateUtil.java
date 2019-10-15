package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing dates with natural language processing.
 */
public class DateUtil {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("E dd MMM yyyy HH:mm:ss z");

    /* Date pattern which allows leading zeroes to be omitted. */
    private static final Pattern DATE_PATTERN = Pattern.compile("([0-9]{1,2})([/\\-])([0-9]{1,2})([/\\-])([0-9]{4})");

    /**
     * Parses a given date in natural language, processes it and returns a formatted date.
     *
     * @param date Input date
     * @return Formatted date as Date type
     * @throws ParseException If the input cannot be parsed
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);

        // Apply a correction to this date input.
        String correctedDate = standardiseDate(date);

        Parser parser = new Parser();
        List<DateGroup> dateGroups = parser.parse(correctedDate);

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
     * Standardises a given date format, from dd/mm/yyyy to yyyy/mm/dd.
     *
     * @param date Date
     * @return A standardised format date
     */
    private static String standardiseDate(String date) {
        StringBuilder builder = new StringBuilder();
        String[] tokens = date.split("\\s+");

        for (String token : tokens) {
            Matcher matcher = DATE_PATTERN.matcher(token);

            if (matcher.matches()) {
                // Manually correct expected dd/mm/yyyy input to yyyy/mm/dd (natty recognised)
                int day = Integer.parseInt(matcher.group(1));
                int month = Integer.parseInt(matcher.group(3));
                int year = Integer.parseInt(matcher.group(5));

                builder = new StringBuilder(String.format("%d/%d/%d", year, month, day));
            } else {
                builder.append(" ").append(token);
            }
        }

        return builder.toString();
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
