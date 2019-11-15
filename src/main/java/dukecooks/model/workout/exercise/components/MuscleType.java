package dukecooks.model.workout.exercise.components;

import static dukecooks.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents an Exercise's muscle type in Duke Cooks.
 * Guarantees: immutable; is valid as declared in {@link #isValidMuscleType(String)}
 */

public class MuscleType {

    public static final String MESSAGE_CONSTRAINTS =
            "MuscleGroups should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String muscleType;

    /**
     * Constructs a {@code Name}.
     *
     * @param muscleType A valid muscleType.
     */
    public MuscleType(String muscleType) {
        requireNonNull(muscleType);
        checkArgument(isValidMuscleType(muscleType), MESSAGE_CONSTRAINTS);
        this.muscleType = muscleType;
    }

    public String getMuscleType() {
        return muscleType;
    }

    /**
     * Returns true if a given string is a valid muscleType.
     */
    public static boolean isValidMuscleType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a voew version of MuscleType.
     */
    public String toView() {
        return "[" + muscleType + "]";
    }

    @Override
    public String toString() {
        return muscleType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MuscleType // instanceof handles nulls
                && muscleType.equals(((MuscleType) other).getMuscleType())); // state check
    }

    @Override
    public int hashCode() {
        return muscleType.hashCode();
    }

}
