package seedu.module.model.module;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's deadline in the module book.
 * Guarantees: immutable; is always valid
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS = "Not a valid Deadline";

    private String description;
    private String time;

    public Deadline(String description, String time) {
        requireNonNull(description);
        this.description = description;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public void setValue(String newValue) {
        this.description = newValue;
    }

    @Override
    public String toString() {
        return description + " ," + time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && description.equals(((Deadline) other).description))
                && time.equals(((Deadline) other).time); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
