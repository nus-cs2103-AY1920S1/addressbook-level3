package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's difficulty in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty implements Comparable<Difficulty> {

    public static final String MESSAGE_CONSTRAINTS = "Difficulty should be numeric.";
    public static final double DIFFICULTY_LOWER_BOUND = 0.0;
    public static final double DIFFICULTY_UPPER_BOUND = 5.0;
    public static final double DEFAULT_DIFFICULTY_VALUE = DIFFICULTY_LOWER_BOUND;
    public static final Difficulty DEFAULT_DIFFICULTY = new Difficulty();
    public static final String VALIDATION_REGEX = "\\d+.\\d+";
    public final double value;

    /**
     * Constructs an {@code Difficulty} from a String representing a number.
     *
     * @param difficulty A valid difficulty.
     */
    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        checkArgument(isValidDifficulty(difficulty), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(difficulty);
    }

    private Difficulty() {
        value = DEFAULT_DIFFICULTY_VALUE;
    }

    /**
     * Returns true if a given string matches the default difficulty value.
     */
    public static boolean isDefaultDifficulty(String test) {
        try {
            return Double.parseDouble(test) == DEFAULT_DIFFICULTY_VALUE;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Returns true if a given {@code Difficulty} is the default difficulty.
     */
    public static boolean isDefaultDifficulty(Difficulty test) {
        return test == DEFAULT_DIFFICULTY;
    }

    /**
     * Returns true if a given string is a valid difficulty.
     */
    public static boolean isValidDifficulty(String test) {
        try {
            double parseResult = Double.parseDouble(test);
            // Difficulty cannot be 0.0, and 0.0 is reserved for default difficulty
            return parseResult > DIFFICULTY_LOWER_BOUND
                    && parseResult <= DIFFICULTY_UPPER_BOUND;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && value == ((Difficulty) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public int compareTo(Difficulty o) {
        if (this == o) {
            return 0;
        }
        return Double.compare(this.value, o.value);
    }
}
