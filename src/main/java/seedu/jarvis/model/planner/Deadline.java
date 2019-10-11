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
}
