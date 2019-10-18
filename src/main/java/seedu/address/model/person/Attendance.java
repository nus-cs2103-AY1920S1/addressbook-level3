package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAttendance(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance should be a number";

    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param attendance A valid email address.
     */
    public Attendance(String attendance) {
        requireNonNull(attendance);
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        value = attendance;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidAttendance(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && value.equals(((Attendance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
