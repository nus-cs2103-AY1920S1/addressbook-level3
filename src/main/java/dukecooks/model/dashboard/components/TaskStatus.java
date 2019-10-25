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

    /**
     * Check if task is complete
     */
    public boolean isDone(String complete) {
        if (complete.equals("COMPLETE")) {
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
