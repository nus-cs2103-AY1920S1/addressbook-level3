package seedu.address.calendar.model.util;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;

/**
 * Handles all date related manipulation. This acts as a facade class for {@code MonthOfYearUtil} and
 * {@code DayOfWeekUtil}.
 */
public class DateUtil {
    public static final int FIRST_DAY_OF_MONTH = 1;

    /* The following is used for day-related purposes. */

    /**
     * Converts numerical ({@code int}) representation of day of week into an instance of {@code DayOfWeek}.
     * 0 represents Sunday, 1 represents Monday, etc.
     * Guarantees: {@code dayAsInt} is between 0 and 6 (inclusive)
     *
     * @param dayAsInt Numerical representation of the desired day of week
     * @return {@code DayOfWeek} instance which represents the day of week implied by {@code dayAsInt}
     */
    public static DayOfWeek toDayOfWeek(int dayAsInt) {
        return DayOfWeekUtil.of(dayAsInt);
    }

    /**
     * Calculates the number of days between two {@code Date} instances. If the two {@code Date} instances represent
     * the same date, the value returned will be zero.
     *
     * @param startDate The start date (should come before or be the same as {@code endDate})
     * @param endDate The end date
     * @return The number of days between the {@code startDate} and {@code endDate}
     */
    public static long daysBetween(Date startDate, Date endDate) {
        checkArgument(isValidDaysBetweenQuery(startDate, endDate), "Start date should be before end date");
        LocalDate start = toLocalDate(startDate);
        LocalDate end = toLocalDate(endDate);
        return ChronoUnit.DAYS.between(start, end);
    }

    private static boolean isValidDaysBetweenQuery(Date startDate, Date endDate) {
        return startDate.compareTo(endDate) <= 0;
    }

    /**
     * Converts date to {@code LocalDate}.
     *
     * @param date The date to be converted
     * @return A {@code LocalDate} representation of the specified date
     */
    private static LocalDate toLocalDate(Date date) {
        int dayOfMonth = date.getDay().getDayOfMonth();
        int month = date.getMonth().getNumericalVal();
        int year = date.getYear().getNumericalValue();

        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * Gets the first {@code Date} in the specified month of the specified year.
     *
     * @param monthOfYear The specified month
     * @param year The specified year
     * @return The first {@code Date} in the specified month of the specified year
     */
    public static Date getFirstDateInMonth(MonthOfYear monthOfYear, Year year) {
        assert Day.isValidDayOfMonth(FIRST_DAY_OF_MONTH, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day firstDay = Day.getDay(FIRST_DAY_OF_MONTH, monthOfYear, year);

        return new Date(firstDay, monthOfYear, year);
    }

    /**
     * Gets the last {@code Date} in the specified month of the specified year.
     *
     * @param monthOfYear The specified month
     * @param year The specified year
     * @return The last {@code Date} in the specified month of the specified year
     */
    public static Date getLastDateInMonth(MonthOfYear monthOfYear, Year year) {
        int lastDayOfMonth = monthOfYear.getNumDaysInMonth(year);

        assert Day.isValidDayOfMonth(lastDayOfMonth, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day lastDay = Day.getDay(lastDayOfMonth, monthOfYear, year);

        return new Date(lastDay, monthOfYear, year);
    }

    /**
     * Gets the first {@code Date} that is in the same month (and year) as the specified date.
     *
     * @param startDate The specified date
     * @return The first {@code Date} in the same month (and year) as the specified date
     */
    public static Date getFirstDateInSameMonth(Date startDate) {
        MonthOfYear monthOfYear = startDate.getMonth();
        Year year = startDate.getYear();

        assert Day.isValidDayOfMonth(FIRST_DAY_OF_MONTH, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day firstDay = Day.getDay(FIRST_DAY_OF_MONTH, monthOfYear, year);

        return new Date(firstDay, monthOfYear, year);
    }

    /**
     * Gets the last {@code Date} that is in the same month (and year) as the specified date.
     *
     * @param startDate The specified date
     * @return The last {@code Date} in the same month (and year) as the specified date
     */
    public static Date getLastDateInSameMonth(Date startDate) {
        MonthOfYear monthOfYear = startDate.getMonth();
        Year year = startDate.getYear();
        int lastDayOfMonth = monthOfYear.getNumDaysInMonth(year);

        assert Day.isValidDayOfMonth(lastDayOfMonth, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day lastDay = Day.getDay(lastDayOfMonth, monthOfYear, year);

        return new Date(lastDay, monthOfYear, year);
    }

    /* The following is used for month-related purposes. */
    /**
     * Converts an {@code int} which represents a month in Java's default {@code Calendar} class to an instance of
     * {@code MonthOfYear}.
     * Guarantees: {@code javaMonth} is between 0 and 11 (inclusive)
     *
     * @param javaMonth {@code int} representation of a month in Java's default {@code Calendar} class
     * @return {@code MonthOfYear} equivalent of {@code javaMonth}
     */
    public static MonthOfYear convertJavaMonth(int javaMonth) {
        return MonthOfYearUtil.convertJavaMonth(javaMonth);
    }

    /**
     * Checks whether the given string of nth-length matches any of the first nth-letters of a valid month.
     * The given string must contain at least 3 letters.
     *
     * @param month The given string
     * @return {@code true} if {@code month} is a valid representation of a valid month and contains at least 3
     *          letters
     */
    public static boolean isValidMonthStr(String month) {
        return MonthOfYearUtil.isValidMonthStr(month);
    }

    /**
     * Gets the number of days in the specified month of the specified year.
     *
     * @param monthOfYear The specified month
     * @param year The specified year
     * @return The number of days in the specified month of the specified year
     */
    public static int getNumDaysInMonth(MonthOfYear monthOfYear, Year year) {
        return monthOfYear.getNumDaysInMonth(year);
    }

    /**
     * Checks whether {@code monthNum} is a valid zero-based representation of a month, i.e.
     * the values should be between 0 and 11 (inclusive).
     *
     * @param monthNum Numerical value which represents a month
     * @return {@code true} if the numerical value is a valid zero-based representation of a month
     */
    public static boolean isValidMonthNum(int monthNum) {
        return MonthOfYearUtil.isValidZeroBasedMonthNum(monthNum);
    }

    /**
     * Converts a valid {@code String} representation of a month to an instance of {@code MonthOfYear}.
     * Guarantees: The given string is a valid representation and contains at least 3 letters
     *
     * @param monthStr The valid {@code String} representation of a month
     * @return {@code MonthOfYear} instance that is represented by {@code monthStr}
     */
    public static MonthOfYear convertStrToMonth(String monthStr) {
        return MonthOfYearUtil.convertStrToMonth(monthStr);
    }

    /**
     * Converts a zero-based representation of a month to an instance of {@code MonthOfYear}.
     * Guarantees: {@code zeroBasedMonth} is between 0 and 11 (inclusive)
     *
     * @param zeroBasedMonth Zero-based, numerical representation of a month
     * @return @code MonthOfYear} equivalent of {@code monthNum}
     */
    public static MonthOfYear convertNumToMonth(int zeroBasedMonth) {
        return MonthOfYearUtil.convertZeroBasedNumToMonth(zeroBasedMonth);
    }

    /* The following is used for more specific month-and-day-related purposes. */

    /**
     * Gets all the {@code Day}s of the specified month in the specified year.
     *
     * @param monthOfYear The specified month
     * @param year The specified year
     * @return All the {@code Day}s of the specified month in the specified year
     */
    public static List<Day> getDaysOfMonth(MonthOfYear monthOfYear, Year year) {
        return Day.getDaysOfMonth(monthOfYear, year);
    }

    /**
     * Computes which day (of week) {@code monthOfYear} starts on.
     * Guarantees: computation will only happen for valid month of year and year values
     *
     * @param monthOfYear Numerical representation of a month (the value should be between 1 and 12 inclusive)
     * @param year Numerical representation of a year (the value should be between 1980 and 2200 inclusive)
     * @return Day (of week) {@code this} month starts on
     */
    public static int findFirstDayOfWeekAsNum(int monthOfYear, int year) {
        checkArgument((monthOfYear > 0 && monthOfYear <= 12) && (year >= 1980 && year <= 2200),
                "Invalid numerical representation of month of year/year");
        int monthNumerical = monthOfYear;
        int zellerMonth = findZellerMonth(monthNumerical);
        int zellerYear = findZellerYear(zellerMonth, year);
        int zellerCentury = zellerYear / 100;
        int lastTwoDigitsOfYear = zellerYear % 100;

        // use Zeller's formula
        int day = ((FIRST_DAY_OF_MONTH + (13 * zellerMonth - 1) / 5 + lastTwoDigitsOfYear + (lastTwoDigitsOfYear / 4)
                + (zellerCentury / 4) - 2 * zellerCentury)) % 7;
        int positiveDay = day < 0 ? (day + 7) : day;

        return positiveDay;
    }

    /**
     * Computes the numerical value of {@code this} month such that it can be easily used with Zeller's rule.
     *
     * @param monthNumerical Numerical representation of {@code this} month
     * @return Numerical value of {@code this} month such that it can be easily used with Zeller's rule.
     *          The value should be between 1 and 12 (inclusive).
     */
    private static int findZellerMonth(int monthNumerical) {
        int shiftedMonth = ((monthNumerical - 2) + MonthOfYearUtil.NUM_MONTHS_IN_YEAR)
                % MonthOfYearUtil.NUM_MONTHS_IN_YEAR;
        return shiftedMonth == 0 ? MonthOfYearUtil.NUM_MONTHS_IN_YEAR : shiftedMonth; // since return val > 0 and <= 12
    }

    /**
     * Computes the year such that it can be easily used with Zeller's rule.
     *
     * @param zellerMonth {@code this} month such that it can be easily used with Zeller's rule
     * @return Year such that it can be easily used with Zeller's rule.
     */
    private static int findZellerYear(int zellerMonth, int year) {
        return zellerMonth > 10 ? (year - 1) : year;
    }
}
