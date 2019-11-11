package seedu.address.model.note;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Type of class.
 */
public class ClassType {
    public static final String MESSAGE_CONSTRAINTS =
            "Types should only contain the registered types of notes. "
                    + "Eg. tutorials(tut)/lab/consultations(c)/sectionals(s)/preparation_time(p)";

    public static final String VALIDATION_REGEX = "tutorials|tut|lab|consultations|c|sectionals|s|preparation_time|p";

    public final String type;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid class type.
     */
    public ClassType(String type) {
        requireNonNull(type);
        checkArgument(isValidClassType(type), MESSAGE_CONSTRAINTS);
        this.type = type;
    }

    /**
     * Returns true if a given string is a valid class type.
     */
    public static boolean isValidClassType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassType // instanceof handles nulls
                && type.equals(((ClassType) other).type)); // state check
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
