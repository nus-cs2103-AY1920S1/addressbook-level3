package seedu.exercise.model.exercise.exceptions;

/**
 * Signals that the operation will result in duplicate exercises (Exercises are considered duplicates if they have
 * the same identity).
 */
public class DuplicateExerciseException extends RuntimeException {
    public DuplicateExerciseException() {
        super("Operation would result in duplicate exercises.");
    }
}
