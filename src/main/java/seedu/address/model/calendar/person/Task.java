package seedu.address.model.calendar.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.calendar.tag.Tag;


/**
 * Represents a Task in the taskPlace book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final TaskTitle taskTitle;
    private final TaskTime taskTime;
    private final TaskDescription taskDescription;

    // Data fields
    private final TaskPlace taskPlace;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(TaskTitle taskTitle, TaskTime taskTime, TaskDescription taskDescription, TaskPlace taskPlace, Set<Tag> tags) {
        requireAllNonNull(taskTitle, taskTime, taskDescription, taskPlace, tags);
        this.taskTitle = taskTitle;
        this.taskTime = taskTime;
        this.taskDescription = taskDescription;
        this.taskPlace = taskPlace;
        this.tags.addAll(tags);
    }

    public TaskTitle getTaskTitle() {
        return taskTitle;
    }

    public TaskTime getTaskTime() {
        return taskTime;
    }

    public TaskDescription getTaskDescription() {
        return taskDescription;
    }

    public TaskPlace getTaskPlace() {
        return taskPlace;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same taskTitle have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getTaskTitle().equals(getTaskTitle())
                && (otherTask.getTaskTime().equals(getTaskTime()) || otherTask.getTaskDescription().equals(getTaskDescription()));
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
                && otherTask.getTaskTime().equals(getTaskTime())
                && otherTask.getTaskDescription().equals(getTaskDescription())
                && otherTask.getTaskPlace().equals(getTaskPlace())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskTitle, taskTime, taskDescription, taskPlace, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskTitle())
                .append(" TaskTime: ")
                .append(getTaskTime())
                .append(" TaskDescription: ")
                .append(getTaskDescription())
                .append(" Address: ")
                .append(getTaskPlace())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
