package seedu.module.model.module;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's deadline in the module book.
 * Guarantees: immutable; is always valid
 */
public class Deadline {
    private String value;

    public Deadline(String deadline) {
        requireNonNull(deadline);
        value = deadline;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String newValue) {
        value = newValue;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
