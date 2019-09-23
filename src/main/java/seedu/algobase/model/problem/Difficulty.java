package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's difficulty in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty {

    public static final String MESSAGE_CONSTRAINTS = "Difficulty should be numeric";
    public static final String VALIDATION_REGEX = "^\\d+$";

    public final String value;

    /**
     * Constructs an {@code Difficulty}.
     *
     * @param difficulty A valid difficulty.
     */
    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        checkArgument(isValidDifficulty(difficulty), MESSAGE_CONSTRAINTS);
        value = difficulty;
    }

    /**
     * Returns true if a given string is a valid difficulty.
     */
    public static boolean isValidDifficulty(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && value.equals(((Difficulty) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
