package seedu.address.model.tasks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.DateTime;

// @@author bruceskellator

/**
 * Represents a Builder responsible for creating {@link TaskSource}.
 */
public class TaskSourceBuilder {

    // Required
    private final String description;

    // Optional
    private DateTime due;
    // private Duration expectedDuration;
    private Set<String> tags;
    private boolean isDone;

    TaskSourceBuilder(String description) {
        this.description = Objects.requireNonNull(description);
    }

    @JsonCreator
    private TaskSourceBuilder(@JsonProperty("description") String description,
                               @JsonProperty("due") DateTime due,
                               @JsonProperty("tags") Set<String> tags,
                               @JsonProperty("done") boolean isDone) {
        this.description = description;
        this.due = due;
        this.tags = tags;
        this.isDone = isDone;
    }

    public TaskSourceBuilder setDueDate(DateTime due) {
        this.due = due;
        return this;
    }

    public TaskSourceBuilder setDone(boolean done) {
        isDone = done;
        return this;
    }

    public TaskSourceBuilder setTags(Collection<String> tags) {
        this.tags = new HashSet<>(tags);
        return this;
    }

    public TaskSource build() {
        return new TaskSource(this);
    }

    String getDescription() {
        return description;
    }

    DateTime getDueDate() {
        return due;
    }

    boolean isDone() {
        return isDone;
    }

    Set<String> getTags() {
        return tags;
    }
}
