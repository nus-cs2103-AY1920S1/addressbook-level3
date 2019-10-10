package seedu.jarvis.model.financetracker.exceptions;

/**
 * Signals that the operation will result in a negative spending limit being set for the user.
 */
public class NegativeLimitException extends RuntimeException {
    public NegativeLimitException() {
        super("Operation will result in a negative spending limit");
    }
}
