package seedu.algobase.model.problem.exceptions;

/**
 * Signals that the operation will result in duplicate Problems (Problems are considered duplicates if they have the
 * same identity).
 */
public class DuplicateProblemException extends RuntimeException {
    public DuplicateProblemException() {
        super("Operation would result in duplicate problems");
    }
}
