package seedu.jarvis.model.planner;

import java.util.Calendar;

/**
 * Represents a Deadline task in JARVIS
 */
public class Deadline extends Task {

    private Calendar deadline;

    public Deadline(String taskDes, Calendar deadline) {
        super(taskDes);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Deadline: " + this.taskDes + " by " + this.deadline;
    }
}
