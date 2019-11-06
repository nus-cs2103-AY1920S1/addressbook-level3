package seedu.address.calendar.model.util;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DateUtil {
    public static final int FIRST_DAY_OF_MONTH = 1;

    /* The following is used for day-related purposes. */
    public static DayOfWeek toDayOfWeek(int dayAsInt) {
        return DayOfWeekUtil.of(dayAsInt);
    }

    public static long daysBetween(Date startDate, Date endDate) {
        LocalDate start = toLocalDate(startDate);
        LocalDate end = toLocalDate(endDate);
        return ChronoUnit.DAYS.between(start, end);
    }

    private static LocalDate toLocalDate(Date date) {
        int dayOfMonth = date.getDay().getDayOfMonth();
        int month = date.getMonth().getNumericalVal();
        int year = date.getYear().getNumericalValue();

        return LocalDate.of(year, month, dayOfMonth);
    }

    public static Date getFirstDateInMonth(MonthOfYear monthOfYear, Year year) {
        assert Day.isValidDayOfMonth(FIRST_DAY_OF_MONTH, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day firstDay = Day.getDay(FIRST_DAY_OF_MONTH, monthOfYear, year);

        return new Date(firstDay, monthOfYear, year);
    }

    public static Date getLastDateInMonth(MonthOfYear monthOfYear, Year year) {
        int lastDayOfMonth = monthOfYear.getNumDaysInMonth(year);

        assert Day.isValidDayOfMonth(lastDayOfMonth, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day lastDay = Day.getDay(lastDayOfMonth, monthOfYear, year);

        return new Date(lastDay, monthOfYear, year);
    }

    public static Date getFirstDateInSameMonth(Date startDate) {
        MonthOfYear monthOfYear = startDate.getMonth();
        Year year = startDate.getYear();

        assert Day.isValidDayOfMonth(FIRST_DAY_OF_MONTH, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day firstDay = Day.getDay(FIRST_DAY_OF_MONTH, monthOfYear, year);

        return new Date(firstDay, monthOfYear, year);
    }

    public static Date getLastDateInSameMonth(Date startDate) {
        MonthOfYear monthOfYear = startDate.getMonth();
        Year year = startDate.getYear();
        int lastDayOfMonth = monthOfYear.getNumDaysInMonth(year);

        assert Day.isValidDayOfMonth(lastDayOfMonth, monthOfYear, year) : Day.MESSAGE_INVALID_DAY_RANGE_ERROR;
        Day lastDay = Day.getDay(lastDayOfMonth, monthOfYear, year);

        return new Date(lastDay, monthOfYear, year);
    }

    /* The following is used for year-related purposes. */
    public static int getNumericalVal(DayOfWeek dayOfWeek) {
        return dayOfWeek.getNumericalVal();
    }

    /* The following is used for month-related purposes. */
    public static MonthOfYear convertJavaMonth(int javaMonth) {
        return MonthOfYearUtil.convertJavaMonth(javaMonth);
    }

    public static boolean isValidMonthStr(String month) {
        return MonthOfYearUtil.isValidMonthStr(month);
    }

    public static int getNumDaysInMonth(MonthOfYear monthOfYear, Year year) {
        return monthOfYear.getNumDaysInMonth(year);
    }

    public static boolean isValidMonthNum(int montNum) {
        return MonthOfYearUtil.isValidMonthNum(montNum);
    }

    public static MonthOfYear convertStrToMonth(String monthStr) {
        return MonthOfYearUtil.convertStrToMonth(monthStr);
    }

    public static MonthOfYear convertNumToMonth(int zeroBasedMonth) {
        return  MonthOfYearUtil.convertNumToMonth(zeroBasedMonth);
    }

    /* The following is used for more specific month-and-day-related purposes. */

    public static List<Day> getDaysOfMonth(MonthOfYear monthOfYear, Year year) {
        return Day.getDaysOfMonth(monthOfYear, year);
    }

    /**
     * Computes which day (of week) {@code} month starts on
     *
     * @return Day (of week) {@code this} month starts on
     */
    public static int findFirstDayOfWeekAsNum(int monthOfYear, int year) {
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
