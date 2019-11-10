package seedu.address.model.claim.exceptions;

//@@author{weigenie}
/**
 * Signals that the operation will result in duplicate Claims (Claims are considered duplicates if they have the same
 * identity).
 */
public class DuplicateClaimException extends RuntimeException {
    public DuplicateClaimException() {
        super("Operation would result in duplicate claims");
    }
}
