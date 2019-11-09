package seedu.address.model.currency.exceptions;

/**
 * Signals that the operation is unable to remove the specified {@code CustomisedCurrency}.
 */
public class CurrencyNotRemovableException extends RuntimeException {

    public CurrencyNotRemovableException() {
        super("Operation cannot be completed as the required customised currency cannot be removed with user command");
    }

}
