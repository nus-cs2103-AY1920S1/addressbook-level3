package seedu.sgm.model.food.exception;

/**
 * Signals that the operation will result in duplicate Foods. Foods are considered duplicates if they have the same
 * name.
 */
public class DuplicateFoodException extends RuntimeException {
    public DuplicateFoodException() {
        super("Operation would result in duplicate foods");
    }
}
