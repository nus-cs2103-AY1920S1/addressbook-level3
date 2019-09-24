package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's difficulty in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty {

    public static final String MESSAGE_CONSTRAINTS = "Difficulty should be numeric.";
    public static final Difficulty DEFAULT_DIFFICULTY = new Difficulty("0.0");
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

    /**
     * Returns true if a given string is a valid difficulty.
     */
    public static boolean isValidDifficulty(String test) {
        try {
            double parseResult = Double.parseDouble(test);
            return parseResult > 0.0 && parseResult <= 5.0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Returns true if a given {@code Difficulty} is default.
     */
    public static boolean isDefaultDifficulty(Difficulty difficulty) {
        return difficulty.equals(DEFAULT_DIFFICULTY);
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

}
