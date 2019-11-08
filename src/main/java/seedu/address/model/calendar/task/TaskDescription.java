package seedu.address.model.calendar.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's description.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class TaskDescription {

    public static final String MESSAGE_CONSTRAINTS = "Description can take any value";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code TaskDescription}.
     *
     * @param description A valid description address.
     */
    public TaskDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDescription // instanceof handles nulls
                && value.equals(((TaskDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
