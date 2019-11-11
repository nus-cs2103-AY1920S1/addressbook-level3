package mams.model.appeal.exceptions;

/**
 * Signals that the operation will result in duplicate Appeals (Appeals are considered duplicates
 * if they have the same identity).
 */
public class DuplicateAppealException extends RuntimeException {
    public DuplicateAppealException() {
        super("Operation would result in duplicate appeal");
    }
}
