package seedu.address.model.calendar.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}
 */
public class TaskDay {

    public static final String MESSAGE_CONSTRAINTS =
            "TaskDay numbers should only contain numbers, and it should be at least 3 digits long";
    public final String value;

    /**
     * Constructs a {@code TaskDay}.
     *
     * @param phone A valid phone number.
     */
    public TaskDay(String phone) {
        requireNonNull(phone);
        phone = phone.toLowerCase();
        checkArgument(isValidDay(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDay(String test) {
        switch (test) {
        case "monday":
        case "tuesday":
        case "wednesday":
        case "thursday":
        case "friday":
        case "saturday":
        case "sunday":
            return true;
        default:
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDay // instanceof handles nulls
                && value.equals(((TaskDay) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
