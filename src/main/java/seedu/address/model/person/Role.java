package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a {@code Person}'s role in the Scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS =
            "Roles are restricted to either an 'interviewee' or an 'interviewer'.";
    public static final String VALIDATION_REGEX = "(interviewee|interviewer)";
    public final String value;

    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role;
    }

    /**
     * Returns true if the given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the equivalent {@code RoleType} enumeration of the valid role, null if it doesn't exist.
     */
    public RoleType getRole() {
        if (value.equals("interviewee")) {
            return RoleType.INTERVIEWEE;
        }
        if (value.equals("interviewer")) {
            return RoleType.INTERVIEWER;
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Role // instanceof handles nulls
                && value.equals(((Role) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
