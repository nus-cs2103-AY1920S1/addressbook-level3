package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.classid.ClassId;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskTime;

/**
 * Represents a Task in the calendar.
 */
public class Reminder {
    // Identity fields
    private final Task task;
    private final ClassId classId;
    private final Set<TaskTime> taskTimeSet = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Reminder(Task task) {
        requireAllNonNull(task);
        this.task = task;
        this.classId = task.getClassId();
    }



    public ClassId getClassId() {
        return classId;
    }

    public Set<TaskTime> getTime() {
        return Collections.unmodifiableSet(taskTimeSet);
    }


    /**
     * Returns true if both tasks has the same description.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }

        return otherReminder != null
                && otherReminder.getClassId().equals(getClassId());
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
        return otherPerson.getClassId().equals(getClassId())
                && otherPerson.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(task);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Module: ")
                .append(getClassId())
                .append(" Time: ")
                .append(getTime())
                .append(" Status: ");
        return builder.toString();
    }
}
