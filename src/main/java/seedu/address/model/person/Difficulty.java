package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Questions's Difficulty in the test bank.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty {


    //TODO: Change this constraint and implement the new constraint
    public static final String MESSAGE_CONSTRAINTS =
            "Difficulty should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code Difficulty}.
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
