package seedu.address.model;

import java.util.List;
import java.util.Objects;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

// @@author bruceskellator

/**
 * Represents a snapshot of Horo's events and tasks.
 * It is immutable. All events and tasks are unique.
 */
public class ModelData {
    private final UniqueOrderedSet<EventSource> events;
    private final UniqueOrderedSet<TaskSource> tasks;

    public ModelData() {
        this.events = new UniqueOrderedSet<>();
        this.tasks = new UniqueOrderedSet<>();
    }

    public ModelData(List<EventSource> events, List<TaskSource> tasks) {
        this.events = deepCopyEvents(events);
        this.tasks = deepCopyTasks(tasks);
    }

    /**
     * Create a deep copy of ModelData
     * @param data the ModelData to clone
     */
    public ModelData(ModelData data) {
        this.events = deepCopyEvents(data.events);
        this.tasks = deepCopyTasks(data.tasks);
    }

    // Return an unmodifiable list.
    public List<EventSource> getEvents() {
        return deepCopyEvents(this.events).toUnmodifiableList();
    }

    public List<TaskSource> getTasks() {
        return deepCopyTasks(this.tasks).toUnmodifiableList();
    }

    /**
     * Get a deep copy of events.
     * Ensures that events are unique.
     * @return an immutable, deep copy list of events
     */
    private UniqueOrderedSet<EventSource> deepCopyEvents(Iterable<EventSource> events) {
        UniqueOrderedSet<EventSource> result = new UniqueOrderedSet<>();
        for (EventSource event : events) {
            // Create a deep-copy of each EventSource.
            result.add(new EventSource(event));
        }
        return result;
    }

    /**
     * Get a deep copy of tasks
     * Ensures that tasks are unique.
     * @return an immutable, deep copy list of tasks
     */
    private UniqueOrderedSet<TaskSource> deepCopyTasks(Iterable<TaskSource> tasks) {
        UniqueOrderedSet<TaskSource> result = new UniqueOrderedSet<>();
        for (TaskSource task : tasks) {
            // Create a deep-copy of each EventSource.
            result.add(new TaskSource(task));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ModelData) {
            ModelData m = (ModelData) o;
            return Objects.equals(this.events, m.events)
                && Objects.equals(this.tasks, m.tasks);
        }
        return false;
    }
}
