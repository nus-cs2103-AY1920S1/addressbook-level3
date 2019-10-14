package seedu.jarvis.model.planner.tasks;

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

    /**
     * Retrieves the start date of the event
     * @return the calendar object that represents the start date
     */
    protected Calendar getStartDate() {
        return start;
    }

    /**
     * Retrieves the end date of the event
     * @return the calendar object that represents the end date
     */
    protected Calendar getEndDate() {
        return end;
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description && same start and end dates
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public boolean isEqual(Task other) {
        boolean isSameType = other instanceof Event;
        boolean isSameDes = taskDes.equals(other.taskDes);
        boolean isSameStartDate = false;
        boolean isSameEndDate = false;
        if (isSameType) {
            Event eOther = (Event) other;
            isSameStartDate = start.compareTo(eOther.getStartDate()) == 0;
            isSameEndDate = end.compareTo(eOther.getEndDate()) == 0;
        }

        return isSameType && isSameDes && isSameStartDate & isSameEndDate;
    }

    @Override
    public String toString() {
        return "Event: " + this.taskDes + " from " + this.start + " to " + this.end;
    }
}
