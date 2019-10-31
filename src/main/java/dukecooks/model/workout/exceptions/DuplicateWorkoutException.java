package dukecooks.model.workout.exceptions;

/**
 * Signals that the operation will result in duplicate Workouts (Workouts are considered duplicates if they have
 * the same identity).
 */
public class DuplicateWorkoutException extends RuntimeException {
    public DuplicateWorkoutException() {
        super("Operation would result in duplicate workouts.");
    }
}
