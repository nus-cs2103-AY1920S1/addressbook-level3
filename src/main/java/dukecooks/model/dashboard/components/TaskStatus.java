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
        if (taskStatus.equals("RECENTLY COMPLETED")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getDoneStatus() {
        if (taskStatus.equals("COMPLETED") || (taskStatus.equals("RECENTLY COMPLETED"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getNotDoneStatus() {
        if (taskStatus.equals("NOT COMPLETE")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return taskStatus;
    }

}
