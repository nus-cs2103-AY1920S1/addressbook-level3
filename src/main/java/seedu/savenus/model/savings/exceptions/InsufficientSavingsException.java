package seedu.savenus.model.savings.exceptions;

//@@author fatclarence
/**
 * Signals that the user is requesting to withdraw too much money from the savings account.
 */
public class InsufficientSavingsException extends RuntimeException {
    public InsufficientSavingsException() {
        super("Insufficient savings in your savings account!");
    }
}
