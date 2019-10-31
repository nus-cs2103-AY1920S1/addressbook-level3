package seedu.address.model.tasks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.DateTime;


/**
 * Represents a Builder responsible for creating {@link TaskSource}.
 */
public class TaskSourceBuilder {

    // Required
    private String description;
    private DateTime dueDate;
    private boolean isCompleted;

    //Optional
    //private Duration expectedDuration;
    private Set<String> tags;

    public TaskSourceBuilder(String description, DateTime dueDate) {
        this.description = Objects.requireNonNull(description);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.isCompleted = false;
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
        return dueDate;
    }

    boolean getCompletionStatus() {
        return isCompleted;
    }

    Set<String> getTags() {
        return tags;
    }
}
