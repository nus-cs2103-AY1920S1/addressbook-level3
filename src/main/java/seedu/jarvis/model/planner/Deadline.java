package seedu.jarvis.model.planner;

import java.util.Date;

/**
 * Represents a Deadline task in JARVIS
 */
public class Deadline extends Task {

    private Date deadline;

    public Deadline(String task_des, Date deadline) {
        super(task_des);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Deadline: " + this.task_des + " by " + this.deadline;
    }
}
