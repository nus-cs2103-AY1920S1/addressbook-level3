package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents point in time within PalPay.
 * Guarantees:  immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time objects must adhere to the format: HHmm\n";

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    private final LocalTime time;

    public Time(String value) {
        requireNonNull(value);
        checkArgument(isValidTime(value), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(value, TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidTime(String test) {
        try {
            TIME_FORMATTER.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return this.time.format(TIME_FORMATTER).hashCode();
    }

    @Override
    public String toString() {
        return this.time.format(TIME_FORMATTER);
    }

}
