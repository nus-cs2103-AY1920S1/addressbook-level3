package dukecooks.model.dashboard.components;

/**
 * Represents a TaskStatus in DukeCooks.
 */
public class TaskStatus {

    public final String taskStatus;

    /**
     * Constructs a {@TaskStatus}
     */
    public TaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public boolean getRecentlyDoneStatus() {
        return taskStatus.equals("RECENTLY COMPLETED");
    }

    public boolean getDoneStatus() {
        return taskStatus.equals("COMPLETED") || (taskStatus.equals("RECENTLY COMPLETED"));
    }

    public boolean getNotDoneStatus() {
        return taskStatus.equals("NOT COMPLETE");
    }

    @Override
    public String toString() {
        return taskStatus;
    }

}
