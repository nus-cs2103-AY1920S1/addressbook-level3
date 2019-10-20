package seedu.address.model;

/**
 * Signals that the operation will result in duplicate {@code Identifiable} objects.
 */
public class DuplicateIdentityException extends RuntimeException {
    public DuplicateIdentityException() {
        super("Operation would result in duplicates");
    }
}
