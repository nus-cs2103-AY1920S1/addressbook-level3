package seedu.jarvis.model.planner;

import java.util.Date;

/**
 * Represents an Event task in JARVIS
 */
public class Event extends Task {

    private Date start;
    private Date end;

    public Event(String task_des, Date start, Date end) {
        super(task_des);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Event: " + this.task_des + " from " + this.start + " to " + this.end;
    }
}
