package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assignment's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAssignmentName(String)}
 */
public class AssignmentName {

    public static final String MESSAGE_CONSTRAINTS =
            "Assignment names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code AssignmentName}.
     *
     * @param assignmentName A valid assignment name.
     */
    public AssignmentName(String assignmentName) {
        requireNonNull(assignmentName);
        checkArgument(isValidAssignmentName(assignmentName), MESSAGE_CONSTRAINTS);
        value = assignmentName;
    }

    /**
     * Returns true if a given string is a valid assignment name.
     */
    public static boolean isValidAssignmentName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentName // instanceof handles nulls
                && value.equals(((AssignmentName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
