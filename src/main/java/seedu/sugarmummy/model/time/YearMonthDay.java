package seedu.sugarmummy.model.time;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a year, month and day.
 */
public class YearMonthDay {
    public static final String VALIDATION_REGEX = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
    public static final String MESSAGE_CONSTRAINTS = "The format of year month day should be yyyy-mm-dd and "
            + "the number should be valid.";
    public static final String VALIDATION_PATTERN_STRING = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(VALIDATION_PATTERN_STRING);

    private LocalDate yearMonthDay;

    public YearMonthDay(String yearMonthDay) {
        assert isValidYearMonthDay(yearMonthDay);
        requireNonNull(yearMonthDay);
        this.yearMonthDay = LocalDate.parse(yearMonthDay, DATE_TIME_FORMATTER);
    }

    public YearMonthDay(LocalDate localDate) {
        requireNonNull(localDate);
        this.yearMonthDay = localDate;
    }

    /**
     * Returns true if a given string is a valid year, month and day.
     */
    public static boolean isValidYearMonthDay(String test) {
        requireNonNull(test);
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        String[] dayMonthYear = test.split("-");
        try {
            LocalDate.of(Integer.parseInt(dayMonthYear[0]),
                    Integer.parseInt(dayMonthYear[1]),
                    Integer.parseInt(dayMonthYear[2]));
            return true;
        } catch (NumberFormatException | DateTimeException e) {
            return false;
        }
    }

    public int getYear() {
        return yearMonthDay.getYear();
    }

    public int getMonth() {
        return yearMonthDay.getMonthValue();
    }

    public int getDay() {
        return yearMonthDay.getDayOfMonth();
    }

    public LocalDate getYearMonthDay() {
        return yearMonthDay;
    }

    public YearMonth getYearMonth() {
        return new YearMonth(yearMonthDay.getYear(), yearMonthDay.getMonthValue());
    }

    public static YearMonthDay getToday() {
        return new YearMonthDay(LocalDate.now());
    }

    @Override
    public String toString() {
        return yearMonthDay.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof YearMonthDay
                        && ((YearMonthDay) other).yearMonthDay.equals(yearMonthDay));
    }

    @Override
    public int hashCode() {
        return yearMonthDay.hashCode();
    }
}
