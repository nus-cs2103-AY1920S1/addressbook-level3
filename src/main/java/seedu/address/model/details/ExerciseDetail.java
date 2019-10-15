package seedu.address.model.details;

/**
 * Represents an Exercise Information in Duke Cooks.
 * Guarantees: immutable; name is valid as declared in {@link #isValidExerciseDetail(String)}
 */
public abstract class ExerciseDetail<T> {

    public static final String MESSAGE_CONSTRAINTS = "Exercise detail should "
            + "be alphanumeric and have at most one magnitude";
    private static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

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

