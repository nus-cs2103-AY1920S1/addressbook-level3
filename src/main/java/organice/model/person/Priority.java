package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's priority for treatment in ORGANice.
 * The higher the priority, the more likely they will be matched with donor's organs.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_CONSTRAINTS = "Priority can only be high, medium or low"
            + "and it should not be blank";

    /*
     * The first character of the priority must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public static final String PRIORITY_HIGH = "high";
    public static final String PRIORITY_MEDIUM = "medium";
    public static final String PRIORITY_LOW = "low";


    public final String value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_CONSTRAINTS);
        value = priority.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(VALIDATION_REGEX)
                && (test.toLowerCase().equals(PRIORITY_HIGH) || test.toLowerCase().equals(PRIORITY_MEDIUM)
                        || test.toLowerCase().equals(PRIORITY_LOW));
    }

    /**
     * Checks if the {@code Priority} is high.
     * @return boolean if this Priority value is 'high'
     */
    public boolean isHighPriority() {
        return value.toLowerCase().equals(PRIORITY_HIGH);
    }

    /**
     * Checks if the {@code Priority} is medium.
     * @return boolean if this Priority value is 'medium'
     */
    public boolean isMediumPriority() {
        return value.toLowerCase().equals(PRIORITY_MEDIUM);
    }

    /**
     * Checks if the {@code Priority} is low.
     * @return boolean if this Priority value is 'low'
     */
    public boolean isLowPriority() {
        return value.toLowerCase().equals(PRIORITY_LOW);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Priority // instanceof handles nulls
            && value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
