package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final TaskStatus taskStatus;
    private LocalDateTime deadline = null;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, TaskStatus taskStatus, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.taskStatus = taskStatus;
        this.tags.addAll(tags);
    }

    // TODO add multiple constructors so that users can add aditional info later

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean hasDeadline() {
        return deadline != null;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Name getName() {
        return name;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both tasks of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        // TODO change the logic to check for the identity fields of status and member
        // basically the name cannot be the same, that's it
        return otherTask != null
            && otherTask.getName().equals(getName());
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

        if (otherTask.hasDeadline() != hasDeadline()) {
            return false;
        }

        if (hasDeadline()) {
            return otherTask.getName().equals(getName())
                    && (otherTask.getTaskStatus() == getTaskStatus())
                    && otherTask.getDeadline().equals(getDeadline())
                    && otherTask.getTags().equals(getTags());
        } else {
            return otherTask.getName().equals(getName())
                    && (otherTask.getTaskStatus() == getTaskStatus())
                    && otherTask.getTags().equals(getTags());
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("Task status: " + getTaskStatus().getDisplayName())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        if (hasDeadline()) {
            String formattedDeadline = getDeadline().format(DateTimeFormatter
                            .ofLocalizedDateTime(FormatStyle.MEDIUM)
                            .withLocale(Locale.UK));
            builder.append(" Deadline: ")
                    .append(formattedDeadline);
        }
        return builder.toString();
    }
}
