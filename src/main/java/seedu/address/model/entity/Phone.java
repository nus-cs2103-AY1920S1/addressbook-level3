package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Entity's phone number in the address book.
 * Only applies to Team, Participant and Mentor.
 * Guarantees: email address is present and not null,
 * field values is  validated as declared in {@link #isValidPhone(String)}, immutable.
 */
public class Phone {

    // Constants
    private static final String SPECIAL_CHARACTERS = "-. ";
    private static final String DIGITS = "\\d+";

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers be of the format (optional)country-code + phone-number "
                    + "and should adhere to the following constraints:\n"
                    + "1. The country code, if it is not existent in constructor, "
                    + "the country code '+65' will be added. "
                    + "2. The phone number should be at least three digit long.\n "
                    + "The phone number must:\n"
                    + "    -contain numbers\n"
                    + "    -contain these special characters only, excluding parentheses,"
                    + "(" + SPECIAL_CHARACTERS + ").";
    public static final String COUNTRY_CODE_REGEX = "\\+" + DIGITS;
    public static final String PHONE_NUMBER_PART_REGEX = "[" + SPECIAL_CHARACTERS + "]" + "?" + DIGITS;
    public static final String PHONE_NUMBER_REGEX = "(" + PHONE_NUMBER_PART_REGEX + ")" + "*";
    public static final String VALIDATION_REGEX = COUNTRY_CODE_REGEX + PHONE_NUMBER_REGEX;

    // Data fields
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number, which is present and not null.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        if (!(phone.charAt(0) == '+')) {
            String newPhone = "+65 " + phone;
            checkArgument(isValidPhone(newPhone), MESSAGE_CONSTRAINTS);
            value = newPhone;
        } else {
            checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
            value = phone;
        }
    }

    /**
     * Returns if a given string is a valid phone number.
     *
     * @param test Phone number.
     * @return boolean whether test is in valid phone number format.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns string representation of object.
     *
     * @return Email address in string format.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns string representation of object, for storage.
     *
     * @return Email address in string format.
     */
    public String toStorageValue() {
        return this.toString();
    }

    /**
     * Returns true if both Phone objects have the same data fields.
     * This defines a stronger notion of equality between two Phone object.
     *
     * @param other Other Phone object.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
