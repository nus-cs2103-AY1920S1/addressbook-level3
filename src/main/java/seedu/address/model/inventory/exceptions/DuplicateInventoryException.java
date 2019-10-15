package seedu.address.model.inventory.exceptions;

/**
 * Signals that the operation will result in duplicate inventories (Inventories are considered duplicates if
 * they have the same identity).
 */
public class DuplicateInventoryException extends RuntimeException {
    public DuplicateInventoryException() {
        super("Operation would result in duplicate tasks");
    }
}
