package seedu.revision.model.answerable;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

/**
 * Represents a Questions's Difficulty in the test bank.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty implements Comparable<Difficulty> {

    public static final String MESSAGE_CONSTRAINTS =
            "Difficulty should only contain numbers, and it should only be 1, 2, or 3";
    public static final String VALIDATION_REGEX = "[1-3]";
    public final String difficulty;

    /**
     * Constructs a {@code Difficulty}.
     *
     * @param difficulty A valid difficulty.
     */
    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        checkArgument(isValidDifficulty(difficulty), MESSAGE_CONSTRAINTS);
        this.difficulty = difficulty;
    }

    /**
     * Returns true if a given string is a valid difficulty.
     */
    public static boolean isValidDifficulty(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return difficulty;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && difficulty.equals(((Difficulty) other).difficulty)); // state check
    }

    @Override
    public int hashCode() {
        return difficulty.hashCode();
    }

    @Override
    public int compareTo(Difficulty o) {
        return this.difficulty.compareTo(o.difficulty);
    }
}
