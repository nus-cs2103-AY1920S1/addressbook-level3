package seedu.savenus.model.wallet.exceptions;

//@@author raikonen
/**
 * Signals that the budget amount is insufficient;
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds in wallet");
    }
}

