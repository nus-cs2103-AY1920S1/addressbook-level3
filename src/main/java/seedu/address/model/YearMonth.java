package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a year and a month.
 */
public class YearMonth {
    public static final String MESSAGE_CONSTRAINTS = "The format of year month should be yyyy-mm and "
        + "the number should be valid.";
    public static final String VALIDATION_PATTERN_STRING = "yyyy-MM";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(VALIDATION_PATTERN_STRING);

    private java.time.YearMonth yearMonth;

    public YearMonth(String yearMonth) {
        assert isValidYearMonth(yearMonth);
        requireNonNull(yearMonth);
        this.yearMonth = java.time.YearMonth.parse(yearMonth, DATE_TIME_FORMATTER);
    }

    public YearMonth(java.time.YearMonth yearMonth) {
        requireNonNull(yearMonth);
        this.yearMonth = yearMonth;
    }

    public YearMonth(int year, int month) {
        this.yearMonth = java.time.YearMonth.of(year, month);
    }

    /**
     * Returns true if a given string is a valid year and month.
     */
    public static boolean isValidYearMonth(String test) {
        try {
            java.time.YearMonth.parse(test, DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public int getYear() {
        return yearMonth.getYear();
    }

    public int getMonth() {
        return yearMonth.getMonthValue();
    }

    public java.time.YearMonth getYearMonth() {
        return java.time.YearMonth.of(getYear(), getMonth());
    }

    @Override
    public String toString() {
        return yearMonth.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof YearMonth
            && ((YearMonth) other).getYearMonth().equals(yearMonth));
    }

    @Override
    public int hashCode() {
        return yearMonth.hashCode();
    }
}
