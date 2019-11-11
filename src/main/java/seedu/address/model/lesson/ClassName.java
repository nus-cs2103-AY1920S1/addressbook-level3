package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Class name in the Classroom.
 */
public class ClassName {

    public static final String MESSAGE_CONSTRAINTS =
            "Class names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String className;

    public ClassName(String className) {
        requireNonNull(className);
        this.className = className;
    }

    /**
     * Returns true if a given string is a valid class name.
     */
    public static boolean isValidClassName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return className;
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassName // instanceof handles nulls
                && className.equals(((ClassName) other).className)); // state check
    }
}
