package seedu.jarvis.model.planner;

import java.util.Date;

/**
 * Represents an Event task in JARVIS
 */
public class Event extends Task {

    private Date start;
    private Date end;

    public Event(String taskDes, Date start, Date end) {
        super(taskDes);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Event: " + this.taskDes + " from " + this.start + " to " + this.end;
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    protected Boolean isEqual(Task other) {
        return (other instanceof Event) && taskDes.equals(other.taskDes);
    }
}
