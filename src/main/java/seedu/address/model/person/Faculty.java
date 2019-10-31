package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the faculty an {@code Interviewee} belongs to.
 */
public class Faculty {

    public static final String MESSAGE_CONSTRAINTS = "Faculties can take any values, but should not be blank.";
    public static final String VALIDATION_REGEX = "[^\\s].*"; // "" will be wrong
    public final String faculty;

    /**
     * Constructs a {@code Faculty}
     *
     * @param faculty a valid faculty.
     */
    public Faculty(String faculty) {
        requireNonNull(faculty);
        checkArgument(isValidFaculty(faculty), MESSAGE_CONSTRAINTS);
        this.faculty = faculty;
    }

    /**
     * Returns true if the given string is a valid faculty.
     */
    public static boolean isValidFaculty(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return faculty;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Faculty // instanceof handles nulls
                && faculty.equals(((Faculty) other).faculty)); // state check
    }

    @Override
    public int hashCode() {
        return faculty.hashCode();
    }
}
