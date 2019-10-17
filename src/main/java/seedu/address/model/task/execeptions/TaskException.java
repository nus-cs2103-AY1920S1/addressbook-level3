package seedu.address.model.task.execeptions;

/**
 * Represents an error that relates to a task.
 */
public class TaskException extends RuntimeException {
    public TaskException(String message) {
        super(message);
    }
}
