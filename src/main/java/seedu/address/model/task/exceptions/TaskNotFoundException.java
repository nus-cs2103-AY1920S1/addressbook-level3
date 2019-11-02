package seedu.address.model.task.exceptions;

/**
 * Represents an error when a tasks is not found in the list.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("Task not found.");
    }
}
