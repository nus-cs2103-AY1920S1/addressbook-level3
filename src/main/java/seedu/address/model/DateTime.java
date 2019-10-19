package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Represents a date or time (optional) within PalPay.
 * Guarantees:  immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime objects must adhere to the format: DDMMYYYY [HH:mm]";

    public final String value;

    public static final String VALIDATION_REGEX_DATE = "(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])\\d{4}";

    public static final String VALIDATION_REGEX_DATE_TIME = "(0?[1-9]|[12][0-9]|3[01])(0?[1-9]|1[0-2])\\d{4} (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9])";

    public DateTime(String value) {
        requireNonNull(value);
        checkArgument(isValidDate(value)||isValidDateTime(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX_DATE);
    }

    /**
     * Returns true if a given string is a valid date-time.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(VALIDATION_REGEX_DATE_TIME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
