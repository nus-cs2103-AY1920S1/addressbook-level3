package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Exercise's name in Duke Cooks.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ExerciseName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String exerciseName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ExerciseName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        exerciseName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return exerciseName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExerciseName // instanceof handles nulls
                && exerciseName.equals(((ExerciseName) other).exerciseName)); // state check
    }

    @Override
    public int hashCode() {
        return exerciseName.hashCode();
    }

}
