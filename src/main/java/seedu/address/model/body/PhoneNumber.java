package seedu.address.model.body;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.regex.Pattern;

//@@author ambervoong
/**
 * Represents a phone number in Mortago.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhoneNumber(String)}
 */
public class PhoneNumber {
    public static final String VALID_NUMBER = "A valid phone number contains exactly 8 digits only."
            + "The country calling code is omitted and assumed to be +65.";

    public final String phoneNumber;

    // Constructor
    public PhoneNumber(String phoneNumber) {
        requireNonNull(phoneNumber);
        checkArgument(isValidPhoneNumber(phoneNumber), VALID_NUMBER);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns if an object is equal to this phone number. Two PhoneNumber objects are equal
     * if their phoneNumber Strings are equal. A null object is not considered equal.
     * @param o Any object.
     * @return a boolean representing the equality of this and o.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    /**
     * Checks if a phone number is valid. A valid phone number contains exactly 8 digits only.
     * The country calling code is omitted and assumed to be +65.
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Area of improvement: check for correct starting digits too
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        return (phoneNumber.length() == 8) && (pattern.matcher(phoneNumber).matches());
    }
}
