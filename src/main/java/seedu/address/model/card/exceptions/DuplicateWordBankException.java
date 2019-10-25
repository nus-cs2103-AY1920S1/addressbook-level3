package seedu.address.model.card.exceptions;

/**
 * Signals that the operation will result in duplicate WordBank (WordBank are considered duplicates if they have the same
 * names).
 */
public class DuplicateWordBankException extends Exception {
    public DuplicateWordBankException() {
        super("Operation would result in duplicate WordBank");
    }
}
