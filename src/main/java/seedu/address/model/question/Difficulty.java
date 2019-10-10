package seedu.address.model.question;

import static java.util.Objects.requireNonNull;

public class Difficulty {
    public static final String MESSAGE_CONSTRAINT = "Difficulty should begin with a non-white space character "
            + "and only covers the existed difficulty in the database";

    public final String difficulty;

    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        this.difficulty = difficulty;
    }

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
