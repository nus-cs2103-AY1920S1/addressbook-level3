package seedu.address.model.earnings;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a type of earnings.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            "Types should only contain the registered types of earnings. "
                    + "Eg. tutorials(tut)/lab/consultations(c)/sectionals(s)/preparation_time(p)";
    public static final String VALIDATION_REGEX = "tutorials|tut|lab|consultations|c|sectionals|s|preparation_time|p";

    public final String type;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        this.type = type;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && type.equals(((Type) other).type)); // state check
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

}
