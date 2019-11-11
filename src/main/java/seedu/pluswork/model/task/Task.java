package seedu.pluswork.model.task;

import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import seedu.pluswork.model.tag.Tag;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final Name name;
    private final TaskStatus taskStatus;
    private LocalDateTime deadline = null;
    private Instant timeStart = null;
    private Instant timeEnd = null;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */


    public Task(Name name, TaskStatus taskStatus, Set<Tag> tags) {
        requireAllNonNull(name, taskStatus, tags);
        this.name = name;
        this.taskStatus = taskStatus;

        if (taskStatus.equals(TaskStatus.DOING)) {
            this.timeStart = Instant.now();
        }

        if (taskStatus.equals(TaskStatus.DONE)) {
            this.timeEnd = Instant.now();
        }

        this.tags.addAll(tags);
    }

    /* Used by {@code EditTaskCommand}, and various tests */
    public Task(Name name, TaskStatus taskStatus, Set<Tag> tags, LocalDateTime dateTime) {
        requireAllNonNull(name, tags, taskStatus); // deadline may be null if task does not have one
        this.name = name;
        this.taskStatus = taskStatus;
        this.tags.addAll(tags);
        this.deadline = dateTime;

        if (taskStatus.equals(TaskStatus.DOING)) {
            this.timeStart = Instant.now();
        }

        if (taskStatus.equals(TaskStatus.DONE)) {
            this.timeEnd = Instant.now();
        }
    }

    public Task() {
        name = null;
        taskStatus = null;
    }

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

    public Instant getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Instant timeStart) {
        this.timeStart = timeStart;
    }

    public Instant getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Instant timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeSpent() {
        String timeTaken = " ";
        if (timeEnd == null) {
            timeTaken = " Task has yet to be completed ";
        } else if (timeStart == null) {
            timeTaken = " Task inputted was done from time of input.";
        } else {
            Duration timeElasped = Duration.between(timeStart, timeEnd);
            long timeInHours = timeElasped.toHours();

            if (timeInHours == 0) {
                timeTaken = "Time taken to complete task: " + timeElasped.toMinutes() + " minutes";
            } else {
                timeTaken = "Time taken to complete task: " + timeInHours + " hours";
            }
        }

        return timeTaken;
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
                .append(" Task status: " + getTaskStatus().getDisplayName())
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

    public String toStringShort() {
        return name.fullName;
    }
}
