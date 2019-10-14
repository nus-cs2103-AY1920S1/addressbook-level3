package seedu.address.model.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's SerialNumber in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidSerialNumber(String)}.
 */
public class SerialNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Serial numbers should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the serial number must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public SerialNumber(String serialNumber) {
        requireNonNull(serialNumber);
        checkArgument(isValidSerialNumber(serialNumber), MESSAGE_CONSTRAINTS);
        value = serialNumber;
    }

    /**
     * Returns true if a given string is a valid serial number.
     */
    public static boolean isValidSerialNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SerialNumber // instanceof handles nulls
                && value.equals(((SerialNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
