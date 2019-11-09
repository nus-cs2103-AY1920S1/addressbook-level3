package seedu.address.model.currency.exceptions;

/**
 * Signals that the operation is unable to find the specified {@code CustomisedCurrency}.
 */
public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException() {
        super("Operation cannot be completed as the required customised currency is not found");
    }

}
