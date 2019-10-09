package seedu.address.model.task.execeptions;

/**
 * Represents an error when a tasks is not found in the list.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("Task not found.");
    }
}
