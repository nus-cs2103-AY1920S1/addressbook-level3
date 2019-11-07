package seedu.address.model.tasks;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.DateTime;

/**
 * Represents a TaskSource in Horo.
 * It is immutable.
 */
public class TaskSource {

    // Required
    private final String description;

    // Optional
    private final DateTime due;
    private final Set<String> tags;
    private final boolean isDone;

    TaskSource(TaskSourceBuilder taskSourceBuilder) {
        this.description = taskSourceBuilder.getDescription();
        this.isDone = taskSourceBuilder.isDone();
        this.due = taskSourceBuilder.getDueDate();
        if (taskSourceBuilder.getTags() == null) {
            this.tags = new HashSet<>();
        } else {
            this.tags = taskSourceBuilder.getTags();
        }
    }

    /**
     * Copy constructor.
     * Creates a deep-copy of an TaskSource.
     * @param taskSource the taskSource to deep-copy.
     */
    public TaskSource(TaskSource taskSource) {
        this.description = taskSource.description;
        this.due = taskSource.due;
        this.isDone = taskSource.isDone;
        this.tags = taskSource.tags;
    }

    public static TaskSourceBuilder newBuilder(String description) {
        return new TaskSourceBuilder(description);
    }

    @JsonProperty("description")
    public String getDescription() {
        return this.description;
    }

    @JsonProperty("due")
    public DateTime getDueDate() {
        return this.due;
    }

    @JsonProperty("done")
    public boolean isDone() {
        return isDone;
    }

    @JsonProperty("tags")
    public Set<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TaskSource) {
            TaskSource t = (TaskSource) object;
            return Objects.equals(this.description, t.description)
                && Objects.equals(this.due, t.due)
                && Objects.equals(!this.isDone, !t.isDone)
                && Objects.equals(this.tags, t.tags);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description,
            this.due,
            this.tags);
    }
}
