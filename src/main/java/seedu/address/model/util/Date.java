package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date within PalPay.
 * Guarantees:  immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS = "Invalid date.\n"
        + "Date objects must adhere to the format: DDMMYYYY\n";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

    public final LocalDate date;

    public Date(String value) {
        requireNonNull(value);
        checkArgument(isValidDate(value), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(value, DATE_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            DATE_FORMATTER.parse(test);
            return checkDate(test);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks for valid date in the Gregorian calendar.
     */
    private static boolean checkDate(String date) {
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(2, 4));
        int year = Integer.parseInt(date.substring(4));

        if (day < 1) {
            return false;
        }

        // For months with 30 days.
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day <= 30) {
            return true;
        }
        // For months with 31 days.
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
            && day <= 31) {
            return true;
        }
        // For February.
        if (month == 2) {
            if (day <= 28) {
                return true;
            } else if (day == 29) {
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    return true;
                }
            }
        }

        // Invalid date.
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Date // instanceof handles nulls
            && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return this.date.format(DATE_FORMATTER).hashCode();
    }

    @Override
    public String toString() {
        return this.date.format(DATE_FORMATTER);
    }

    /**
     * TODO: remove and refactor
     */
    public LocalDate toLocalDate() {
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
}
