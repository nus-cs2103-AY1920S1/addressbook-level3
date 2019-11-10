package seedu.sugarmummy.model.time;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

/**
 * Represents a year and a month.
 */
public class YearMonth {
    public static final String VALIDATION_REGEX = "^[0-9]{4}-(0[1-9]|1[0-2])$";
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

    public YearMonth(int year, int month) {
        this.yearMonth = java.time.YearMonth.of(year, month);
    }

    /**
     * Returns true if a given string is a valid year and month.
     */
    public static boolean isValidYearMonth(String test) {
        requireNonNull(test);
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        String[] dayMonthYear = test.split("-");
        try {
            java.time.YearMonth.of(Integer.parseInt(dayMonthYear[0]),
                    Integer.parseInt(dayMonthYear[1]));
            return true;
        } catch (NumberFormatException | DateTimeException e) {
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
