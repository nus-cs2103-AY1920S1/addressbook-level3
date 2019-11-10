package seedu.address.calendar.parser;

import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

/**
 * A parser which parses day.
 */
public class DayParser {
    private static final String MESSAGE_NON_INT_DAY_ERROR = "Invalid day. Day should be represented numerically.";
    private static final String DAY_EXTRA_ARG = "after day";

    /**
     * Parses the user day input.
     *
     * @param dayStr User day input
     * @return An {@code int} which represents a day of the month
     * @throws ParseException if the given input cannot be represented by an {@code int}
     */
    private int parseDayOfMonth(String dayStr) throws ParseException {
        try {
            int dayOfMonth = Integer.parseInt(dayStr);
            return dayOfMonth;
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_NON_INT_DAY_ERROR);
        }
    }

    /**
     * Parses the {@code int} representation of day of month by considering the specified month and year. Upon parsing,
     * a representative {@code Day} instance will be returned.
     *
     * @param dayOfMonth The specified day of month
     * @param monthOfYear The specified month of year
     * @param year The specified year
     * @return The {@code Day} instance which represents the day of month of the specified month and year
     * @throws ParseException if the specified day of month is invalid
     */
    private Day parse(int dayOfMonth, MonthOfYear monthOfYear, Year year) throws ParseException {
        if (!Day.isValidDayOfMonth(dayOfMonth, monthOfYear, year)) {
            throw new ParseException(Day.MESSAGE_INVALID_DAY_RANGE_ERROR);
        }

        return Day.getDay(dayOfMonth, monthOfYear, year);
    }

    /**
     * Parses the user day input by considering the relevant month and year, if any. Upon parsing, a representative
     * {@code Day} instance will be returned, if any.
     *
     * @param dayInput The user day input
     * @param month The relevant month, if any
     * @param year The relevant year, if any
     * @return A representative {@code Day} instance will be returned, if any
     * @throws ParseException if user day input cannot be parsed successfully
     */
    Optional<Day> parse(Optional<String> dayInput, Optional<MonthOfYear> month, Optional<Year> year)
            throws ParseException {
        if (dayInput.isEmpty()) {
            return Optional.empty();
        }

        String dayOfMonthStr = dayInput.get().trim();

        if (ParserUtil.hasCharInValue(dayOfMonthStr)) {
            throw new ParseException(String.format(ParserUtil.MESSAGE_ARG_EXTRA, DAY_EXTRA_ARG));
        }

        int dayOfMonth = parseDayOfMonth(dayOfMonthStr);

        return Optional.of(parse(dayOfMonth, month, year));
    }

    Day parse(Day startDateDay, Optional<MonthOfYear> month, Optional<Year> year) throws ParseException {
        int dayOfMonth = startDateDay.getDayOfMonth();
        return parse(dayOfMonth, month, year);
    }

    /**
     * Helps to parse the user day input by considering the relevant month and year, if any.
     *
     * @param dayOfMonth The relevant day of month
     * @param month The relevant month
     * @param year The relevant year
     * @return The representative {@code Day} instance
     * @throws ParseException if the day is invalid (does not exist in the specified month and year)
     */
    private Day parse(int dayOfMonth, Optional<MonthOfYear> month, Optional<Year> year) throws ParseException {
        MonthOfYear monthOfYear = month.orElseGet(() -> {
            java.util.Calendar currentDate = java.util.Calendar.getInstance();
            int currentMonth = currentDate.get(java.util.Calendar.MONTH);
            return DateUtil.convertJavaMonth(currentMonth);
        });

        Year yearValue = year.orElseGet(() -> {
            java.util.Calendar currentDate = java.util.Calendar.getInstance();
            int currentYear = currentDate.get(java.util.Calendar.YEAR);
            return new Year(currentYear);
        });

        return parse(dayOfMonth, monthOfYear, yearValue);
    }

}
