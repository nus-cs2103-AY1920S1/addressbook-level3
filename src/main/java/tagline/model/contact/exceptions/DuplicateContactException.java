package tagline.model.contact.exceptions;

/**
 * Signals that the operation will result in duplicate Contacts
 * (Contacts are considered duplicates if they have the same identity).
 */
public class DuplicateContactException extends RuntimeException {
    public DuplicateContactException() {
        super("Operation would result in duplicate contacts");
    }
}
