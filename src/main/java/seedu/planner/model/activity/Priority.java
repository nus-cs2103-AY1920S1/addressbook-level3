package seedu.planner.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

//@@author oscarsu97

/**
 * Represents the priority of an Activity in the application.
 * Guarantees: immutable;
 */
public class Priority {

    public static final String MESSAGE_CONSTRAINTS =
            "Value should be range from 1 to 7  (1 indicates highest priority, 7 indicates the lowest priority) ";
    public static final String VALIDATION_REGEX = "^[0-7]$";

    public final Integer priorityValue;

    /**
     * Constructs a {@code Priority}.
     *
     * @param value A valid priority value.
     */
    public Priority(Integer value) {
        requireNonNull(value);
        checkArgument(isValidPriority(value.toString()), MESSAGE_CONSTRAINTS);
        this.priorityValue = value;
    }

    /**
     * Returns true if a given integer is a valid priority value.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(priorityValue);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && priorityValue == ((Priority) other).priorityValue); // state check
    }

    @Override
    public int hashCode() {
        return  priorityValue.hashCode();
    }
}

