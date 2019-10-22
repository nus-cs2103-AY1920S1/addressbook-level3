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

    public static final String MESSAGE_CONSTRAINTS = "Date objects must adhere to the format: DDMMYYYY\n";

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
            test.matches("(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])\\d{4}");
            DATE_FORMATTER.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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

    public static Date now() {
        String today = DATE_FORMATTER.format(LocalDate.now());
        return new Date(today);
    }
}
