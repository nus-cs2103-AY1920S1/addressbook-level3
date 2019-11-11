package seedu.address.model.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Phone's IdentityNumber in the SML.
 * Guarantees: immutable; is valid as declared in {@link #isValidIdentityNumber(String)}.
 */
public class IdentityNumber {

    public static final String MESSAGE_CONSTRAINTS = "IMEIs must contain only digits and be 15 digits long";

    public static final String VALIDATION_REGEX = "\\d{15}";

    public final String value;

    public IdentityNumber(String identityNumber) {
        requireNonNull(identityNumber);
        checkArgument(isValidIdentityNumber(identityNumber), MESSAGE_CONSTRAINTS);
        value = identityNumber;
    }

    /**
     * Returns true if a given string is a valid IMEI.
     */
    public static boolean isValidIdentityNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IdentityNumber // instanceof handles nulls
                && value.equals(((IdentityNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
