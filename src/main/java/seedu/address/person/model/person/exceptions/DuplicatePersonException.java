package seedu.address.person.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePersonException extends RuntimeException {

    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }

    @Override
    public String toString() {
        return "Operation would result in duplicate persons";
    }
}
