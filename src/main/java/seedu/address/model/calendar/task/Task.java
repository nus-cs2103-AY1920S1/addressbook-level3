package seedu.address.model.calendar.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.calendar.tag.TaskTag;

/**
 * Represents a Task in the CalendarAddressbook.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Task {

    // Identity fields
    private final TaskTitle taskTitle;
    private final TaskDay taskDay;
    private final TaskDescription taskDescription;

    // Data fields
    private final boolean isPersistent;
    private final TaskDeadline taskDeadline;
    private final TaskTime taskTime;
    private final Set<TaskTag> taskTags = new HashSet<>();
    private final int week;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskTitle taskTitle, TaskDay taskDay, TaskDescription taskDescription, TaskDeadline taskDeadline,
                TaskTime taskTime, Set<TaskTag> taskTags, int week, boolean isPersistent) {
        this.taskDeadline = taskDeadline;
        requireAllNonNull(taskTitle, taskDay);
        // requireAllNonNull(taskTitle, taskDay, taskDescription, taskTime, taskTags);
        this.taskTitle = taskTitle;
        this.taskDay = taskDay;
        this.taskDescription = taskDescription;
        this.taskTime = taskTime;
        this.taskTags.addAll(taskTags);
        this.week = week;
        this.isPersistent = isPersistent;
    }

    public TaskTitle getTaskTitle() {
        return taskTitle;
    }

    public TaskDay getTaskDay() {
        return taskDay;
    }

    public TaskDescription getTaskDescription() {
        return taskDescription;
    }

    public TaskDeadline getTaskDeadline() {
        return taskDeadline;
    }

    public TaskTime getTaskTime() {
        return taskTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<TaskTag> getTaskTags() {
        return Collections.unmodifiableSet(taskTags);
    }

    public int getWeek() {
        return week;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    /**
     * Returns true if both task of the same taskTitle have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getTaskTitle().equals(getTaskTitle())
                && otherTask.getWeek() == getWeek()
                && (otherTask.getTaskDay().equals(getTaskDay())
                && otherTask.getTaskDeadline().equals(getTaskDeadline()));
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

        Task otherTask = (Task) other;
        return otherTask.getTaskTitle().equals(getTaskTitle())
                && otherTask.getTaskDay().equals(getTaskDay())
                && otherTask.getWeek() == getWeek()
                && otherTask.getTaskDeadline().equals(getTaskDeadline())
                && otherTask.getTaskDescription().equals(getTaskDescription())
                && otherTask.getTaskTime().equals(getTaskTime())
                && otherTask.getTaskTags().equals(getTaskTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskTitle, taskDay, taskDescription, taskTime, taskTags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskTitle())
                .append(" Time: ")
                .append(getTaskDay())
                .append(" Description: ")
                .append(getTaskDescription())
                .append(" Deadline: ")
                .append(getTaskDeadline())
                .append(" Address: ")
                .append(getTaskTime())
                .append(" Tags: ");
        getTaskTags().forEach(builder::append);
        return builder.toString();
    }

}
