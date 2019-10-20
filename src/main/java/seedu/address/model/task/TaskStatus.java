package seedu.address.model.task;

/**
 * Represents the status of a delivery task.
 */
public enum TaskStatus {

    INCOMPLETE,
    ON_GOING,
    COMPLETED;

    public static final String MESSAGE_CONSTRAINTS = "Task status has to be INCOMPLETE / ON_GOING / COMPLETED.";

}
