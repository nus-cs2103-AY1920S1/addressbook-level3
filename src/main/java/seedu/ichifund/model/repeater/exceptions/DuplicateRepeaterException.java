package seedu.ichifund.model.repeater.exceptions;

/**
 * Signals that the operation will result in duplicate Repeaters (Repeaters are considered duplicates if they have the
 * same identity).
 */
public class DuplicateRepeaterException extends RuntimeException {
    public DuplicateRepeaterException() {
        super("Operation would result in duplicate repeaters");
    }
}
