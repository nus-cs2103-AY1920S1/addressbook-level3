package seedu.address.model.person.parameters;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final Phone EMPTY_PHONE_DETAILS = new Phone();
    public static final String MESSAGE_CONSTRAINTS =
            "A valid phone number should contain 8 digits.\n"
            + "The country calling code is omitted and assumed to be +65.\n"
            + "The first digit should be either '8' or '9'";

    private static final String VALIDATION_REGEX = "[89]\\d{7}";

    private final String value;

    private Phone() {
        value = "";
    }

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }
}
