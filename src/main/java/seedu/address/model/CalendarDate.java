package seedu.address.model;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.YearMonth;

import seedu.address.logic.parser.CalendarDateParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents the date without a time, mainly for the UI system.
 */
public class CalendarDate {

    public static final String DAY_MONTH_YEAR_PATTERN = "dd/MM/yyyy";
    public static final String MONTH_YEAR_PATTERN = "MM/yyyy";

    private static final CalendarDateParser DAY_MONTH_YEAR_PARSER =
            new CalendarDateParser(DAY_MONTH_YEAR_PATTERN);
    private static final CalendarDateParser MONTH_YEAR_PARSER =
            new CalendarDateParser(MONTH_YEAR_PATTERN);

    private final LocalDate localDate;

    public CalendarDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static CalendarDate fromDayMonthYearString(String string) throws ParseException {
        return DAY_MONTH_YEAR_PARSER.parse(string);
    }

    public static CalendarDate fromMonthYearString(String string) throws ParseException {
        return MONTH_YEAR_PARSER.parse(string);
    }

    public static CalendarDate now() {
        return new CalendarDate(LocalDate.now());
    }

    public Integer getDay() {
        return localDate.getDayOfMonth();
    }

    public Integer getMonth() {
        return localDate.getMonthValue();
    }

    public Integer getYear() {
        return localDate.getYear();
    }

    /**
     * Returns the English name of the Month.
     *
     * @return English name of the Month.
     */
    public String getEnglishMonth() {
        String monthStr = new DateFormatSymbols().getMonths()[getMonth() - 1].toLowerCase();
        return monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
    }

    /**
     * Returns the day of the week.
     *
     * @return The day of the week.
     */
    public Integer getWeekIndex() {
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * Returns a new CalendarDate of the previous day.
     *
     * @return A new CalendarDate of the previous day.
     */
    public CalendarDate previousDay() {
        LocalDate previousLocalDate = localDate.minusDays(1);
        return new CalendarDate(previousLocalDate);
    }

    /**
     * Returns a new CalendarDate of the next day.
     *
     * @return A new CalendarDate of the next day.
     */
    public CalendarDate nextDay() {
        LocalDate previousLocalDate = localDate.plusDays(1);
        return new CalendarDate(previousLocalDate);
    }

    /**
     * Returns a new CalendarDate of the current day plus the given days.
     *
     * @param days The number of days to add.
     * @return A new CalendarDate of the next days.
     */
    public CalendarDate nextDays(Integer days) {
        LocalDate previousLocalDate = localDate.plusDays(days);
        return new CalendarDate(previousLocalDate);
    }

    /**
     * Returns a new CalendarDate of the current day minus the given days.
     *
     * @param days The number of days to minus off.
     * @return A new CalendarDate of the previous days.
     */
    public CalendarDate previousDays(Integer days) {
        LocalDate previousLocalDate = localDate.minusDays(days);
        return new CalendarDate(previousLocalDate);
    }

    public CalendarDate firstDayOfTheMonth() {
        return new CalendarDate(localDate.withDayOfMonth(1));
    }

    public Integer lengthOfMonth() {
        return YearMonth.of(getYear(), getMonth()).lengthOfMonth();
    }

    public boolean sameDate(Integer day, Integer month, Integer year) {
        return getDay().equals(day) && getMonth().equals(month) && getYear().equals(year);
    }

    public boolean sameMonthYear(Integer month, Integer year) {
        return getMonth().equals(month) && getYear().equals(year);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CalendarDate) {
            CalendarDate calendarDate = (CalendarDate) obj;
            return sameDate(calendarDate.getDay(), calendarDate.getMonth(), calendarDate.getYear());
        }
        return false;
    }

    @Override
    public String toString() {
        return getDay() + "/" + getMonth() + "/" + getYear();
    }


}
