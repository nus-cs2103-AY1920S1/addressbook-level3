package dukecooks.model;

import static dukecooks.commons.util.AppUtil.checkArgument;
import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.GregorianCalendar;
import java.util.Objects;

/**
 *  Represents the a valid date in DD/MM/YYYY format.
 */
public class Date {

    public static final String MESSAGE_DAY_CONSTRAINTS =
            "Day should be numeric and within the range of the specified month!"
            + "It is a required field.";

    public static final String MESSAGE_MONTH_CONSTRAINTS =
            "Month should be numeric and not exceed the number of months in a year."
            + "It is a required field.";

    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "Year should be numeric and greater than 1900."
            + "It is a required field.";

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date entered is invalid!"
            + "It should be expressed in DD/MM/YYYY format";

    public static final String DATE_VALIDATION_REGEX = "(\\d{1,2})[\\/](\\d{1,2})[\\/](\\d{4})";

    private final int day;
    private final int month;
    private final int year;

    /**
     * Constructs a {@code Date}.
     * @param day A valid day.
     * @param month A valid month.
     * @param year A valid year.
     */
    public Date(int day, int month, int year) {
        requireAllNonNull(day, month, year);
        checkArgument(isValidDay(day, month, year), MESSAGE_DAY_CONSTRAINTS);
        checkArgument(isValidMonth(month), MESSAGE_MONTH_CONSTRAINTS);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Constructs a {@code Date} from String.
     * @param date A valid date in String format.
     */
    public static Date generateDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDateFormat(date), MESSAGE_DATE_CONSTRAINTS);
        String[] d = date.split("[\\/]");
        return new Date(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]));
    }

    /**
     * Checks if date is in the correct specified date format.
     */
    public static boolean isValidDateFormat(String date) {
        return date.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Checks if it is valid day based on the month and year specified.
     * @param day day to validate.
     * @param month reference to check day.
     * @param year reference to check day.
     * @return boolean if valid day.
     */
    public static boolean isValidDay(int day, int month, int year) {
        if (day <= 0 || day > 31) {
            return false;
        }
        if (month == 2) {
            return isValidFebruaryDay(day, year);
        }
        if (month % 2 == 1 && month >= 9 && day > 30) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if it is a valid day in February.
     * @param day day to validate.
     * @param year reference to validate day based on whether it is a Gregorian leap year.
     */
    public static boolean isValidFebruaryDay(int day, int year) {
        GregorianCalendar gc = new GregorianCalendar();
        return (day <= 29 && gc.isLeapYear(year)) || (day <= 28 && !gc.isLeapYear(year));
    }

    /**
     * Returns true if month is within valid range.
     */
    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * Returns true if year is within valid range.
     */
    public static boolean isValidYear(int year) {
        return year > 1900;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Date otherDate = (Date) other;
        return (otherDate.getDay() == getDay())
                && (otherDate.getMonth() == getMonth())
                && (otherDate.getYear() == getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02d", getDay()))
                .append("/")
                .append(String.format("%02d", getMonth()))
                .append("/")
                .append(getYear());
        return builder.toString();
    }


}
