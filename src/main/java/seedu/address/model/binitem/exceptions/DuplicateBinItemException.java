package seedu.address.model.binitem.exceptions;

/**
 * Signals that the operation will result in duplicate BinItems (BinItems are considered
 * duplicates if they have the same item, dateDeleted and expiryDate).
 */
public class DuplicateBinItemException extends RuntimeException {
    public DuplicateBinItemException() {
        super("This operation resulted in a duplicate item in the bin. Likely due to a bug in the codebase");
    }
}
