package seedu.address.model;

import seedu.address.logic.parser.CalendarDateParser;
import seedu.address.logic.parser.exceptions.ParseException;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Represents the date without a time, mainly for the UI system.
 */
public class CalendarDate {

    private static final String USER_DAY_MONTH_YEAR_PATTERN = "dd/MM/yyyy";
    private static final String USER_MONTH_YEAR_PATTERN = "MM/yyyy";

    private static final CalendarDateParser DAY_MONTH_YEAR_PARSER =
            new CalendarDateParser(USER_DAY_MONTH_YEAR_PATTERN);
    private static final CalendarDateParser MONTH_YEAR_PARSER =
            new CalendarDateParser(USER_MONTH_YEAR_PATTERN);

    private final LocalDate localDate;

    public CalendarDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public CalendarDate(YearMonth yearMonth) {
        // Creates a CalendarDate at the end of the month, since the day is not necessary,
        this.localDate = yearMonth.atEndOfMonth();
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

    public String getEnglishMonth() {
        String monthStr = new DateFormatSymbols().getMonths()[getMonth() - 1].toLowerCase();
        return monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
    }

    public Integer getWeekIndex() {
        return localDate.getDayOfWeek().getValue();
    }

    public CalendarDate previousDay() {
        LocalDate previousLocalDate = localDate.minusDays(1);
        return new CalendarDate(previousLocalDate);
    }

    public CalendarDate nextDay() {
        LocalDate previousLocalDate = localDate.plusDays(1);
        return new CalendarDate(previousLocalDate);
    }

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
    public String toString() {
        return getDay() + "/" + getMonth() + "/" + getYear();
    }
}
