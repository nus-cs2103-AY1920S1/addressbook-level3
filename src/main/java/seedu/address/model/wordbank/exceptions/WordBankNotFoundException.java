package seedu.address.model.wordbank.exceptions;

/**
 * Signals that the operation is unable to find the specified WordBank.
 */
public class WordBankNotFoundException extends RuntimeException {
    public WordBankNotFoundException() {
        super("No word banks found");
    }
}
