package seedu.sugarmummy.model.biography;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's goal in his / her biography. Guarantees: immutable; name is valid as declared in {@link
 * #isValidGoal(String)}
 */
public class Goal implements ListableField {

    public static final String MESSAGE_CONSTRAINTS = "Goal names can take any values.";
    public static final String MESSAGE_DUPLICATE_INPUTS = "Duplicate inputs have been found. Please check to ensure "
            + "that there are no duplicates in goals entered.";

    public static final String VALIDATION_REGEX = "^$|[^\\s].*";

    public final String goal;

    /**
     * Constructs a {@code Goal}.
     *
     * @param goal A valid goal.
     */
    public Goal(String goal) {
        requireNonNull(goal);
        checkArgument(isValidGoal(goal), MESSAGE_CONSTRAINTS);
        this.goal = goal;
    }

    /**
     * Returns true if a given string is a valid goal.
     */
    public static boolean isValidGoal(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Goal // instanceof handles nulls
                        && goal.equals(((Goal) other).goal)); // state check
    }

    @Override
    public int hashCode() {
        return goal.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return goal;
    }

}
