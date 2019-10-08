package seedu.address.model.task;

/* Enables tracking of task status */
public enum TaskStatus {

    UNBEGUN("Not Started"), DOING("In Progress"), DONE("Completed");

    public static String MESSAGE_CONSTRAINTS = "Invalid task status";

    private String displayName;

    /**
     * Enum constructor to give UI-friendly display names.
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
