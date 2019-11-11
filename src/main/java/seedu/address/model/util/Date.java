package seedu.address.model.util;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * Represents a date within PalPay.
 * Guarantees:  immutable; is valid as declared in {@link #isValid(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_FORMAT_CONSTRAINTS = "Invalid date format.\n"
        + "Date objects must adhere to the format: DDMMYYYY\n";
    public static final String MESSAGE_DATE_INVALID = "Invalid date.\n"
        + "%s is not a valid date in the (Gregorian) calendar";
    public static final String DATE_FORMAT = "\\b\\d{8}\\b";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");
    public static final Date TODAY = now();



    public final LocalDate date;

    public Date(String value) {
        requireNonNull(value);
        checkArgument(isValid(value), MESSAGE_FORMAT_CONSTRAINTS);
        checkArgument(isValidDate(value), String.format(MESSAGE_DATE_INVALID, value));
        this.date = LocalDate.parse(value, DATE_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValid(String test) {
        return isValidFormat(test) && isValidDate(test);
    }

    private static boolean isValidFormat(String test) {
        return test.matches(DATE_FORMAT);
    }

    /**
     * @param test A String representation of a date
     * @return Whether {@code test} represents a valid date in the Gregorian calendar
     */
    public static boolean isValidDate(String test) {

        int day = Integer.parseInt(test.substring(0, 2));
        int month = Integer.parseInt(test.substring(2, 4));
        int year = Integer.parseInt(test.substring(4));
        boolean isLeapYear = Year.isLeap(year);

        return ChronoField.DAY_OF_MONTH.range().isValidIntValue(day) && // Checks if day is potentially valid
            ChronoField.MONTH_OF_YEAR.range().isValidIntValue(month) && // Checks if month is valid
            ChronoField.YEAR.range().isValidIntValue(year) && // Checks if year is valid
            day <= Month.of(month).length(isLeapYear); // Checks if day is valid with relation to year and month
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Date // instanceof handles nulls
            && date.isEqual(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return this.date.format(DATE_FORMATTER).hashCode();
    }

    @Override
    public String toString() {
        return this.date.format(DATE_FORMATTER);
    }

    private LocalDate toLocalDate() {
        return this.date;
    }

    @Override
    public int compareTo(Date date) {
        return this.date.compareTo(date.date);
    }

    /**
     * Get today's date
     *
     * @return Date object of today's date
     */
    public static Date now() {
        String today = DATE_FORMATTER.format(LocalDate.now());
        return new Date(today);
    }

    public static int daysBetween(Date x, Date y) {
        return (int) DAYS.between(x.toLocalDate(), y.toLocalDate());
    }

    public boolean isPast() {
        return daysBetween(TODAY, this) < 0;
    }

    /**
     * Checks if {@code month} is a valid month from 1 to 12 inclusive.
     */
    public static boolean isValidMonth(String month) {
        int monthInt = Integer.parseInt(month);
        return monthInt >= 1 && monthInt <= 12;
    }

    /**
     * Checks if {@code year} is from 1900 to 9999 inclusive.
     */
    public static boolean isValidYear(String year) {
        int yearInt = Integer.parseInt(year);
        return yearInt >= 1900 && yearInt <= 9999;
    }

    /**
     * Checks if {@code month} is same at this {@code date}.
     */
    public boolean isSameMonth(int month) {
        return this.date.getMonthValue() == month;
    }

    /**
     * Checks if {@code year} is same at this {@code date}.
     */
    public boolean isSameYear(int year) {
        return this.date.getYear() == year;
    }
}
