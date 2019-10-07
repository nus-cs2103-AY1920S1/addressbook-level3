package seedu.algobase.model.plan.exceptions;

/**
 * Signals that the operation will result in duplicate Plans (Plans are considered duplicates if they have the
 * same identity).
 */
public class DuplicatePlanException extends RuntimeException {
    public DuplicatePlanException() {
        super("Operation would result in duplicate plans");
    }
}