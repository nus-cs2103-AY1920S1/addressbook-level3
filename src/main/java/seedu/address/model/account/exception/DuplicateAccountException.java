package seedu.address.model.account.exception;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException() {
        super("Operation would result in duplicate accounts");
    }
}
