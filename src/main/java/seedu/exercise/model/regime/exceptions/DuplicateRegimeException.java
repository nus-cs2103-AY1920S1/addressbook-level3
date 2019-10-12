package seedu.exercise.model.regime.exceptions;

/**
 * Signals that the operation will result in duplicate regimes (Regimes are considered duplicates if they have
 * the same name).
 */
public class DuplicateRegimeException extends RuntimeException {
    public DuplicateRegimeException() {
        super("Operation would result in duplicate regimes");
    }
}
