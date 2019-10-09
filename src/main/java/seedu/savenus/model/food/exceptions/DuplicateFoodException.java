package seedu.savenus.model.food.exceptions;

/**
 * Signals that the operation will result in duplicate foods (foods are considered duplicates if they have the same
 * identity).
 */
public class DuplicateFoodException extends RuntimeException {
    public DuplicateFoodException() {
        super("Operation would result in duplicate foods");
    }
}
