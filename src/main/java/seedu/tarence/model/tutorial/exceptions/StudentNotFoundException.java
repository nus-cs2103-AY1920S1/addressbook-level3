package seedu.tarence.model.tutorial.exceptions;

/**
 * Signals that the operation is unable to find the specified Student.
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("Operation is unable to find the specified Student.");
    }
}
