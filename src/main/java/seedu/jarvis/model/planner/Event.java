package seedu.jarvis.model.planner;

import java.util.Calendar;

/**
 * Represents an Event task in JARVIS
 */
public class Event extends Task {

    private Calendar start;
    private Calendar end;

    public Event(String taskDes, Calendar start, Calendar end) {
        super(taskDes);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Event: " + this.taskDes + " from " + this.start + " to " + this.end;
    }
}
