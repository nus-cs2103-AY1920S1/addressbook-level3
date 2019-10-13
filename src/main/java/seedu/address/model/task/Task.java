package seedu.address.model.task;

import java.text.ParseException;
import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Task in the calendar.
 */
public class Task {
    // Identity fields
    private final TaskDescription taskDescription;
    private final TaskTime taskTime;
    private final Marking marking;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskDescription taskDescription, TaskTime taskTime, Marking marking) {
        requireAllNonNull(taskDescription, taskTime, marking);
        this.taskDescription = taskDescription;
        this.taskTime = taskTime;
        this.marking = marking;
    }

    public TaskDescription getDescription() {
        return taskDescription;
    }

    public TaskTime getTime() {
        return taskTime;
    }

    public Marking getMarking() {
        return marking;
    }


    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean hasTimeConflict(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        try {
            return otherTask != null
                    && otherTask.getTime().hasTimeConflict(getTime());
        } catch (ParseException e) {
            return true;
        }
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherPerson = (Task) other;
        return otherPerson.getDescription().equals(getDescription())
                && otherPerson.getTime().equals(getTime())
                && otherPerson.getMarking().equals(getMarking());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskDescription, taskTime, marking);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append(getDescription())
                .append(" Time: ")
                .append(getTime())
                .append(" Status: ")
                .append(getMarking());
        return builder.toString();
    }
}
