package seedu.address.model.task.exceptions;

/**
 * Represents an error that relates to a task.
 */
public class TaskException extends RuntimeException {
    public TaskException(String message) {
        super(message);
    }
}
