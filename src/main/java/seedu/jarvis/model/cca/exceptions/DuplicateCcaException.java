package seedu.jarvis.model.cca.exceptions;

/**
 * Signals that the operation will result in duplicate Ccas.
 */
public class DuplicateCcaException extends RuntimeException {
    public DuplicateCcaException() {
        super("Operation would result in duplicate ccas");
    }
}
