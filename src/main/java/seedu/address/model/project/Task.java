package seedu.address.model.project;


import java.util.Objects;

/**
 * Represents a Project's task in the app.
 * Guarantees: immutable; is always valid
 */
public class Task {

    public final Description description;
    public final Time time;
    public final boolean isDone;

    /**
     * Constructs a {@code Task}.
     *
     * @param description A description of task.
     */
    public Task(Description description, Time time, boolean isDone) {
        this.description = description;
        this.time = time;
        this.isDone = isDone;

    }

    public Task(Description description, boolean isDone) {
        this.description = description;
        this.time = null;
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public Description getDescription() {
        return description;
    }

    public Time getTime() {
        return time;
    }

    public boolean isDone() {
        return isDone;
    }


    @Override
    public String toString() {
        if (time != null) {
            return "[" + this.getStatusIcon() + "] " + description + " by " + time;
        } else {
            return "[" + this.getStatusIcon() + "] " + description;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getDescription().equals(getDescription())
                && otherTask.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, time, isDone);
    }
}


