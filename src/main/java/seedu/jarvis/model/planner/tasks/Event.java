package seedu.jarvis.model.planner.tasks;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents an Event task in JARVIS
 */
public class Event extends Task {

    private LocalDate start;
    private LocalDate end;

    public Event(String taskDes, Priority priority, Frequency frequency, Set<Tag> tags, LocalDate start, LocalDate end) {
        super(taskDes, priority, frequency, tags);
        this.start = start;
        this.end = end;
    }

    public Event(String taskDes, LocalDate start, LocalDate end) {
        this(taskDes, null, null, null, start, end);
    }

    /**
     * Retrieves the start date of the event
     * @return the LocalDate object that represents the start date
     */
    public LocalDate getStartDate() {
        return start;
    }

    /**
     * Retrieves the end date of the event
     * @return the LocalDate object that represents the end date
     */
    public LocalDate getEndDate() {
        return end;
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description && same start and end dates
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Event)) {
            return false;
        }

        boolean isSameDes = taskDes.equals(((Event) other).taskDes);
        Event eOther = (Event) other;
        boolean isSameStartDate = start.compareTo(eOther.getStartDate()) == 0;
        boolean isSameEndDate = end.compareTo(eOther.getEndDate()) == 0;

        return isSameDes && isSameStartDate & isSameEndDate;
    }

    @Override
    public String toString() {
        return "Event: " + this.taskDes + " from " + this.start + " to " + this.end
                + attributesString();

    }
}
