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
}
