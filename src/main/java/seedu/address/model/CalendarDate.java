package seedu.address.model;

import seedu.address.logic.parser.CalendarDateParser;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;

/**
 * Represents the date without a time, mainly for the UI system.
 */
public class CalendarDate {

    // Parsers
    public static final String USER_DAY_MONTH_YEAR_PATTERN = "dd/MM/yyyy";
    public static final String USER_MONTH_YEAR_PATTERN = "MM/yyyy";

    private static final CalendarDateParser DAY_MONTH_YEAR_PARSER = new CalendarDateParser(USER_DAY_MONTH_YEAR_PATTERN);
    private static final CalendarDateParser MONTH_YEAR_PARSER = new CalendarDateParser(USER_MONTH_YEAR_PATTERN);

    private LocalDate localDate;

    public CalendarDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public static CalendarDate fromDayMonthYearString(String string) throws ParseException {
        return DAY_MONTH_YEAR_PARSER.parse(string);
    }

    public static CalendarDate fromMonthYearString(String string) throws ParseException {
        return MONTH_YEAR_PARSER.parse(string);
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

    @Override
    public String toString() {
        return getDay() + "/" + getMonth() + "/" + getYear();
    }
}
