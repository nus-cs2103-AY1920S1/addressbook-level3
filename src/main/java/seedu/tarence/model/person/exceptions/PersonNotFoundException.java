package seedu.tarence.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super ("Operation is unable to find the specified person.");
    }
}
