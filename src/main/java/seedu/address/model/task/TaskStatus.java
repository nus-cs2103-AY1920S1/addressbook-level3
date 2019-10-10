package seedu.address.model.task;

/**
 * Represents a Task's progress status in the project dashboard.
 * Can be changed by the user to reflect change in Task's progress.
 */
public enum TaskStatus {

    UNBEGUN("Not Started"), DOING("In Progress"), DONE("Completed");

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid task status, please enter one of unbegun, doing or done";

    private String displayName;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param displayName an alternate name for the task status
     */
    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

}
