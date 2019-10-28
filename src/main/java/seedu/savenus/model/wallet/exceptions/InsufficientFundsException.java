package seedu.savenus.model.wallet.exceptions;

/**
 * Signals that the budget input is out of bounds;
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds in wallet");
    }
}

