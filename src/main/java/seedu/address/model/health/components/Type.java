package seedu.address.model.health.components;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.health.components.util.TypeUtil.TYPE_LIST;

/**
 * Represents the type of health record.
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            "Record type should only contain alphanumeric characters and spaces, and it should not be blank";

    public final String type;
    public final String unit;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        this.type = type;
        this.unit = TYPE_LIST.get(type);
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return TYPE_LIST.containsKey(test);
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
