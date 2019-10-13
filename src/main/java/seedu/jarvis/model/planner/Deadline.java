package seedu.jarvis.model.planner;

import java.util.Date;

/**
 * Represents a Deadline task in JARVIS
 */
public class Deadline extends Task {

    private Date deadline;

    public Deadline(String taskDes, Date deadline) {
        super(taskDes);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Deadline: " + this.taskDes + " by " + this.deadline;
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    protected Boolean isEqual(Task other) {
        return (other instanceof Deadline) && taskDes.equals(other.taskDes);
    }
}
