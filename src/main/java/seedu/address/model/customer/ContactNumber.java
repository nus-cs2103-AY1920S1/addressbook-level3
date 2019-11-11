package seedu.address.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Customer's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContactNumber(String)}
 */
public class ContactNumber {


    public static final String MESSAGE_CONSTRAINTS =
            "ContactNumber numbers should only contain numbers, and it should be only 8 digits long";
    public static final String VALIDATION_REGEX = "\\d{8}";
    public final String value;

    /**
     * Constructs a {@code ContactNumber}.
     * @param contactNumber A valid contactNumber.
     */
    public ContactNumber(String contactNumber) {
        requireNonNull(contactNumber);
        checkArgument(isValidContactNumber(contactNumber), MESSAGE_CONSTRAINTS);
        value = contactNumber;
    }

    /**
     * Returns true if a given string is a valid contactNumber.
     */
    public static boolean isValidContactNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactNumber // instanceof handles nulls
                && value.equals(((ContactNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
