package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a module's name in the module planner.
 * Guarantees: immutable
 */
public class Name {

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid module name.
     */
    public Name(String name) {
        requireNonNull(name);
        fullName = name;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}

