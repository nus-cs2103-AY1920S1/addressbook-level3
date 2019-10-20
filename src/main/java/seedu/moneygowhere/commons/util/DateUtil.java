package seedu.moneygowhere.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.moneygowhere.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing dates with natural language processing.
 */
public class DateUtil {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_FORMAT_PRETTY = DateTimeFormatter.ofPattern("EE dd/MM/yyyy");

    /** Date pattern which allows leading zeroes to be omitted. **/
    private static final Pattern DATE_PATTERN = Pattern.compile("([0-9]{1,2})(?:[/\\-])([0-9]{1,2})"
            + "(?:(?:[/\\-])([0-9]{0,4})|)");

    /** Parser for natural language processing. **/
    private static final Parser PARSER = new Parser();

    /**
     * Parses a given date in natural language, processes it and returns a formatted date.
     *
     * @param date Input date
     * @return Formatted date as Date type
     * @throws ParseException If the input cannot be parsed
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);

        // Normalises this date input.
        String normalisedDate = normaliseDate(date);

        List<DateGroup> dateGroups = PARSER.parse(normalisedDate);

        if (dateGroups.isEmpty()) {
            throw new ParseException("Invalid input date");
        }

        List<Date> possibleDates = dateGroups.get(0).getDates();

        if (possibleDates.isEmpty()) {
            throw new ParseException("No possible dates from input date");
        }

        List<LocalDate> convertedDate = dateGroups.get(0).getDates()
                .stream()
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toList());

        return convertedDate.get(0);
    }

    /**
     * Parses a given date in natural language, processes it and returns a formatted date.
     *
     * @param date Input date
     * @return List of formatted dates
     */
    public static List<LocalDate> parseDates(String date) {
        requireNonNull(date);

        // Normalises this date input.
        String normalisedDate = normaliseDate(date);

        List<DateGroup> dateGroups = PARSER.parse(normalisedDate);

        if (dateGroups.isEmpty()) {
            return null;
        }

        return dateGroups.get(0).getDates()
                .stream()
                .map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toList());
    }

    /**
     * Checks if an input date is valid.
     *
     * @param date Input date
     * @return True if the input date was valid.
     */
    public static boolean isValidDate(String date) {
        requireNonNull(date);

        // Normalises this date input.
        String normalisedDate = normaliseDate(date);

        List<DateGroup> dateGroups = PARSER.parse(normalisedDate);

        if (dateGroups.isEmpty()) {
            return false;
        }

        List<Date> possibleDates = dateGroups.get(0).getDates();
        return !possibleDates.isEmpty();
    }

    /**
     * Checks if an input date is valid.
     *
     * @param year Year
     * @param month Month
     * @param day Day
     * @return True if the input date was valid.
     */
    private static boolean isValidNormalisedDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Normalises a given date format, from dd/mm/yyyy to yyyy/mm/dd.
     *
     * @param date Date
     * @return A standardised format date
     */
    private static String normaliseDate(String date) {
        // The date should not only contain numbers.
        if (StringUtil.isNumeric(date)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        String[] tokens = date.split("\\s+");

        for (String token : tokens) {
            Matcher matcher = DATE_PATTERN.matcher(token);

            if (matcher.matches()) {
                // Manually correct expected dd/mm/yyyy input to yyyy/mm/dd (natty recognised)
                int day = Integer.parseInt(matcher.group(1));
                int month = Integer.parseInt(matcher.group(2));

                String capturedYear = matcher.group(3);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                if (capturedYear != null && !capturedYear.isEmpty()) {
                    year = Integer.parseInt(capturedYear);

                    // Invalidate year inputs like 79 or 89
                    if (year < 1900) {
                        return "";
                    }
                }

                if (!isValidNormalisedDate(year, month, day)) {
                    return "";
                }

                if (builder.length() > 0) {
                    builder.append(" ");
                }

                builder.append(String.format("%d/%d/%d", year, month, day));
            } else {
                if (tokens.length == 1) {
                    builder.append(token);
                } else {
                    builder.append(" ").append(token);
                }
            }
        }

        return builder.toString();
    }

    /**
     * Formats a date to a string.
     * Example output: 25/12/2019
     *
     * @param date Input date
     * @return A formatted date string
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMAT);
    }

    /**
     * Pretty formats a date to a string.
     * Example output: Mon 25/12/2019
     *
     * @param date Input date
     * @return A formatted date string
     */
    public static String prettyFormatDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMAT);
            return DATE_FORMAT_PRETTY.format(parsedDate);
        } catch (DateTimeParseException e) {
            return "";
        }
    }
}
