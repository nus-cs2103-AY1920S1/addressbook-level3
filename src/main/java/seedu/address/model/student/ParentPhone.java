package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's parent's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidParentPhone(String)}
 */
public class ParentPhone {


    public static final String MESSAGE_CONSTRAINTS =
            "Parent phone numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code ParentPhone}.
     *
     * @param parentPhone A valid parent phone number.
     */
    public ParentPhone(String parentPhone) {
        requireNonNull(parentPhone);
        checkArgument(isValidParentPhone(parentPhone), MESSAGE_CONSTRAINTS);
        value = parentPhone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidParentPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ParentPhone // instanceof handles nulls
                && value.equals(((ParentPhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
