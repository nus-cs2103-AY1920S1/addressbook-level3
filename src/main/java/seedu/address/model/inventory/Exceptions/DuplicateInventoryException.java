package seedu.address.model.inventory.Exceptions;

public class DuplicateInventoryException extends RuntimeException {
    public DuplicateInventoryException() {
        super("Operation would result in duplicate tasks");
    }
}
