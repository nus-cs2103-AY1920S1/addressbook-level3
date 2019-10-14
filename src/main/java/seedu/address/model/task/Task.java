package seedu.address.model.task;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Task in the calendar.
 */
public class Task {
    // Identity fields
    private final TaskDescription taskDescription;
    private final Set<TaskTime> taskTimeSet = new HashSet<>();
    private final Marking marking;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskDescription description, Set<TaskTime> taskTimes, Marking mark) {
        requireAllNonNull(description, taskTimes, mark);
        taskDescription = description;
        taskTimeSet.addAll(taskTimes);
        marking = mark;
    }

    public TaskDescription getDescription() {
        return taskDescription;
    }

    public Set<TaskTime> getTime() {
        return Collections.unmodifiableSet(taskTimeSet);
    }

    public Marking getMarking() {
        return marking;
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
        return Objects.hash(taskDescription, taskTimeSet, marking);
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
