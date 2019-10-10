package seedu.address.model.question;

import static java.util.Objects.requireNonNull;

/**
 * Represents a question's difficulty. Difficulties are only guaranteed immutable and non-blank;
 * in particular there can be duplicates.
 */
public class Difficulty {
    public static final String MESSAGE_CONSTRAINT = "Difficulty should begin with a non-white space character "
            + "and only covers the existed difficulty in the database";

    public final String difficulty;

    /**
     * Constructs a {@code Difficulty}
     *
     * @param difficulty Valid difficulty
     */
    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        this.difficulty = difficulty;
    }

    /**
     * Returns true if a given string is a valid difficulty.
     */
    public static boolean isValidDifficulty(String test) {
        return test.trim().length() > 0;
    }

    @Override
    public String toString() {
        return difficulty;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Difficulty)
                && difficulty.equals(((Difficulty) other).difficulty);
    }

    @Override
    public int hashCode() {
        return difficulty.hashCode();
    }
}
