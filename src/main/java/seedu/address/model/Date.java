package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date within PalPay.
 * Guarantees:  immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Date objects must adhere to the format: DDMMYYYY\n";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyyyy");

    public final LocalDate date;

    public Date (String value) {
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
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && date.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return this.date.format(DATE_FORMATTER).hashCode();
    }

    @Override
    public String toString() {
        return this.date.format(DATE_FORMATTER);
    }

}
