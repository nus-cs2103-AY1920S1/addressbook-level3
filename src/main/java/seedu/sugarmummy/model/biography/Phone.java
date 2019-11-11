package seedu.sugarmummy.model.biography;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's phone number in his / her biography. Guarantees: immutable; is valid as declared in {@link
 * #isValidPhone(String)}
 */
public class Phone implements ListableField {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, at least 3 digits long, and it should not be blank.";
    public static final String MESSAGE_DUPLICATE_INPUTS = "Duplicate inputs have been found. Please check to ensure "
            + "that there are no duplicates in phone numbers entered.";

    public static final String VALIDATION_REGEX = "\\d{3,}";

    public final String phoneNumber;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        phoneNumber = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        requireNonNull(test);
        return !test.isEmpty() && test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                        && phoneNumber.equals(((Phone) other).phoneNumber)); // state check
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return phoneNumber;
    }

}
