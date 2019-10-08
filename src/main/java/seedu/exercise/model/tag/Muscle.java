package seedu.exercise.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.AppUtil.checkArgument;

/**
 * Represents a Muscle tag in ExerHealth.
 * Guarantees: immutable; name is valid as declared in {@link #isValidMuscleName(String)}
 */
public class Muscle {

    public static final String MESSAGE_CONSTRAINTS = "Muscle groups should contain only alphabetical characters";
    public static final String VALIDATION_REGEX = "^[ A-Za-z]+$";

    public final String muscleName;

    /**
     * Constructs a {@code Muscle}.
     *
     * @param muscleName A valid tag name.
     */
    public Muscle(String muscleName) {
        requireNonNull(muscleName);
        checkArgument(isValidMuscleName(muscleName), MESSAGE_CONSTRAINTS);
        this.muscleName = muscleName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidMuscleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Muscle // instanceof handles nulls
                && muscleName.equals(((Muscle) other).muscleName)); // state check
    }

    @Override
    public int hashCode() {
        return muscleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + muscleName + ']';
    }

}
