package seedu.address.model.visittodoitem.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateVisitTodoItemException extends RuntimeException {
    public DuplicateVisitTodoItemException() {
        super("Operation would result in duplicate persons");
    }
}
