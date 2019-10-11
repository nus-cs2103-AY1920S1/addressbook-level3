package seedu.address.model.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's Imei in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidImei(String)}.
 */
public class Imei {

    public static final String MESSAGE_CONSTRAINTS = "IMEIs must contain only digits and be 15 digits long";

    public static final String VALIDATION_REGEX = "\\d{15}";

    public final String value;

    public Imei(String Imei) {
        requireNonNull(Imei);
        checkArgument(isValidImei(Imei), MESSAGE_CONSTRAINTS);
        value = Imei;
    }

    /**
     * Returns true if a given string is a valid Imei.
     */
    public static boolean isValidImei(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Imei // instanceof handles nulls
                && value.equals(((Imei) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
