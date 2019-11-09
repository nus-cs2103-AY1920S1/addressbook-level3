package seedu.moneygowhere.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.moneygowhere.logic.parser.exceptions.ParseException;

//@@author Nanosync
/**
 * Contains utility methods used for parsing dates with natural language processing.
 */
public class DateUtil {
    /** Formal date formatters **/
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_FORMAT_PRETTY = DateTimeFormatter.ofPattern("EE dd/MM/yyyy");
    private static final DateTimeFormatter DATE_FORMAT_TWO_DIGIT_YEAR = DateTimeFormatter.ofPattern("dd/MM/yy");

    /** Date pattern which allows leading zeroes to be omitted (DD/MM/YYYY, DD-MM-YYYY, D-MM-YYYY or DD-M-YYYY) **/
    private static final Pattern DATE_DEFAULT_PATTERN = Pattern.compile("([0-9]{1,2})(?:[/\\-])([0-9]{1,2})"
            + "(?:(?:[/\\-])([0-9]+)|)");

    /** Date pattern with dashes (01-Jan-2019). **/
    private static final Pattern DATE_MONTH_DASH_PATTERN =
            Pattern.compile("([0-9]{1,2})-([a-zA-Z]{3})(?:$|-)([0-9]*)");

    /** Date pattern with spaces (01 Jan 2019). **/
    private static final Pattern DATE_MONTH_SPACE_PATTERN =
            Pattern.compile("([0-9]{1,2}) +([a-zA-Z]{3})(?:$| +)([0-9]*)");

    /** Date pattern with dashes (Jan-01-2019). **/
    private static final Pattern DATE_MONTH_ALT_DASH_PATTERN =
            Pattern.compile("([a-zA-Z]{3})-([0-9]{1,2})(?:$|-)([0-9]*)");

    /** Date pattern with spaces (Jan 01 2019). **/
    private static final Pattern DATE_MONTH_ALT_SPACE_PATTERN =
            Pattern.compile("([a-zA-Z]{3}) +([0-9]{1,2})(?:$| +)([0-9]*)");

    /** Day tokens **/
    private static final String[] DAY_TOKENS =
            new String[] { "mon", "tue", "wed", "thu", "fri", "sat", "sun" };

    /** Month tokens **/
    private static final String[] MONTH_TOKENS =
            new String[] { "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec" };

    /** Parser for natural language processing. **/
    private static final Parser PARSER = new Parser();

    /**
     * Parses a given date in natural language, processes it and returns a formatted date.
     * If there are multiple dates in the date input, return the first date found.
     *
     * @param date Input date
     * @return Formatted date as Date type
     * @throws ParseException If the input cannot be parsed
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);

        List<LocalDate> dates = parseDates(date);

        if (dates == null) {
            throw new ParseException("Invalid input date");
        }

        return dates.get(0);
    }

    /**
     * Parses a given date in natural language, processes it and returns a formatted date.
     *
     * @param date Input date
     * @return List of formatted dates
     */
    public static List<LocalDate> parseDates(String date) {
        requireNonNull(date);

        // Normalises this date input
        String normalisedDate = normaliseDate(date);

        List<DateGroup> dateGroups = PARSER.parse(normalisedDate);

        if (dateGroups.isEmpty()) {
            return null;
        }

        DateGroup dateGroup = dateGroups.get(0);

        // Disallow explicit time input
        if (!dateGroup.isTimeInferred()) {
            return null;
        }

        List<Date> dates = dateGroup.getDates();
        if (dates.isEmpty()) {
            return null;
        }

        return dates.stream()
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

        return parseDates(date) != null;
    }

    /**
     * Checks if an input date is valid.
     *
     * @param year Year
     * @param month Month
     * @param day Day
     * @return True if the input date was valid.
     */
    private static boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Validates a date matcher without the month.
     * Rationale is to let NLP handle the month.
     *
     * @param year Year
     * @param day Day
     * @return True if the matcher input is correct
     */
    private static boolean isMatcherInvalid(String year, int day) {
        return day > 31 || (year != null && year.length() > 4);
    }

    /**
     * Process a matcher based on formatting groups: DD MM YYYY
     *
     * @param matcher Matcher to be processed
     * @return True if the matcher parameter groups were valid.
     */
    private static boolean processMatcher(Matcher matcher, int dayGroup, int monthGroup, int yearGroup) {
        int day = Integer.parseInt(matcher.group(dayGroup));
        String month = matcher.group(monthGroup);
        String year = matcher.group(yearGroup);

        if (isMatcherInvalid(year, day)) {
            return false;
        }

        if (year.isEmpty()) {
            year = String.format("%d", LocalDate.now().getYear());
        }

        for (int i = 0; i < MONTH_TOKENS.length; i++) {
            if (month.trim().equalsIgnoreCase(MONTH_TOKENS[i])) {
                return isValidDate(Integer.parseInt(year), i + 1, day);
            }
        }

        return true;
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

        // Check other formats
        Matcher matcherAlt1 = DATE_MONTH_DASH_PATTERN.matcher(date);
        Matcher matcherAlt2 = DATE_MONTH_SPACE_PATTERN.matcher(date);
        Matcher matcherAlt3 = DATE_MONTH_ALT_DASH_PATTERN.matcher(date);
        Matcher matcherAlt4 = DATE_MONTH_ALT_SPACE_PATTERN.matcher(date);

        if (matcherAlt1.matches()) {
            return processMatcher(matcherAlt1, 1, 2, 3) ? date : "";
        } else if (matcherAlt2.matches()) {
            return processMatcher(matcherAlt2, 1, 2, 3) ? date : "";
        } else if (matcherAlt3.matches()) {
            return processMatcher(matcherAlt3, 2, 1, 3) ? date : "";
        } else if (matcherAlt4.matches()) {
            return processMatcher(matcherAlt4, 2, 1, 3) ? date : "";
        }

        for (String token : tokens) {
            Matcher matcher = DATE_DEFAULT_PATTERN.matcher(token);

            if (matcher.matches()) {
                // Manually correct expected dd/mm/yyyy input to yyyy/mm/dd (natty recognised)
                int day = Integer.parseInt(matcher.group(1));
                int month = Integer.parseInt(matcher.group(2));

                String capturedYear = matcher.group(3);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                if (capturedYear != null && !capturedYear.isEmpty()) {
                    if (capturedYear.length() > 4) {
                        return "";
                    }

                    year = Integer.parseInt(capturedYear);

                    // Invalidate year inputs like 79 or 89
                    if (year < 1900) {
                        return "";
                    }
                }

                if (!isValidDate(year, month, day)) {
                    return "";
                }

                if (builder.length() > 0) {
                    builder.append(" ");
                }

                builder.append(String.format("%d/%d/%d", year, month, day));
            } else {
                // Do not allow day tokens since they cause problems.
                for (String dayToken : DAY_TOKENS) {
                    if (token.equalsIgnoreCase(dayToken)
                            || token.equalsIgnoreCase(dayToken + ",")) {
                        return "";
                    }
                }

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

    //@@author minpyaemoe
    /**
     * Formats a date to a string with two-digit year.
     * Example output: 25/12/19
     *
     * @param date Input date
     * @return A formatted date string
     */
    public static String twoDigitYearFormatDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMAT);
            return DATE_FORMAT_TWO_DIGIT_YEAR.format(parsedDate);
        } catch (DateTimeParseException e) {
            return "";
        }
    }

    //@@author austinsantoso
    /**
     * Returns today's date as a LocalDate.
     *
     * @return The LocalDate which represents today.
     */
    public static LocalDate getTodayDate() {
        return LocalDate.now();
    }

    //@@author minpyaemoe
    /**
     * Returns the number of days between two localDate objects in long.
     *
     * @param d1 first LocalDate
     * @param d2 second LocalDate
     * @return The number of days between first localDate and second
     */
    public static long getDaysBetween(LocalDate d1, LocalDate d2) {
        return ChronoUnit.DAYS.between(d1, d2);
    }
}
