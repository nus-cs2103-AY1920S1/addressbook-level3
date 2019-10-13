package seedu.address.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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

    /**
     * Returns true if a given string is a valid muscleType.
     */
    public static boolean isValidMuscleType(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return muscleType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && muscleType.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return muscleType.hashCode();
    }

}
