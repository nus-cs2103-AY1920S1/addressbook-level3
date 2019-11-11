package seedu.address.model.classid;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's class ID.
 * Guarantees: immutable; is valid as declared in {@link #isValidClassId(String)}
 */
public class ClassId {

    public static final String MESSAGE_CONSTRAINTS = "Class IDs can take any values, and it should not be blank";

    /**
     * The first character of the classId must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code ClassId}.
     *
     * @param classid A valid classId.
     */
    public ClassId(String classid) {
        requireNonNull(classid);
        checkArgument(isValidClassId(classid), MESSAGE_CONSTRAINTS);
        value = classid;
    }

    /**
     * Returns true if a given string is a valid classId.
     */
    public static boolean isValidClassId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassId // instanceof handles nulls
                && value.equals(((ClassId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
