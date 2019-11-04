package seedu.address.model.student.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("Unable to find specified student.");
    }

}
