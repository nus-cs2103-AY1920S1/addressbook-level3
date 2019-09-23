package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's solution in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidSolution(String)}
 */
public class Solution {

    public static final String MESSAGE_CONSTRAINTS = "Solution can take any values, and it should not be blank";

    /*
     * The first character of the solution must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Solution}.
     *
     * @param solution A valid solution.
     */
    public Solution(String solution) {
        requireNonNull(solution);
        checkArgument(isValidSolution(solution), MESSAGE_CONSTRAINTS);
        value = solution;
    }

    /**
     * Returns true if a given string is a valid solution.
     */
    public static boolean isValidSolution(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Solution // instanceof handles nulls
                && value.equals(((Solution) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
