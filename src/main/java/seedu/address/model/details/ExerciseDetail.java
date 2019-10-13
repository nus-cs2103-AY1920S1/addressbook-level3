package seedu.address.model.details;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Exercise Information in Duke Cooks.
 * Guarantees: immutable; name is valid as declared in {@link #isValidExerciseDetail(String)}
 */
public abstract class ExerciseDetail<T> {

    public static final String MESSAGE_CONSTRAINTS = "Exercise detail should be alphanumeric and have only one magnitude";
    private static final String VALIDATION_REGEX = "\\p{Alnum}+{ ?}";

    protected T magnitude;

    public T getMagnitude() {
        return magnitude;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidExerciseDetail(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}

