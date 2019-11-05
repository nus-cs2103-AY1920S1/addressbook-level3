package seedu.address.model;

import java.util.Collections;
import java.util.List;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

/**
 * Represents a snapshot of Horo's events and tasks.
 * It is immutable. All events and tasks are unique.
 */
public class ModelData {
    private final List<EventSource> events;
    private final List<TaskSource> tasks;

    public ModelData() {
        this.events = List.of();
        this.tasks = List.of();
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
        this.events = data.getEvents();
        this.tasks = data.getTasks();
    }

    public List<EventSource> getEvents() {
        return deepCopyEvents(this.events);
    }

    public List<TaskSource> getTasks() {
        return deepCopyTasks(this.tasks);
    }

    /**
     * Get an immutable, deep copy list of events.
     * Ensures that events are unique.
     * @return an immutable, deep copy list of events
     */
    private List<EventSource> deepCopyEvents(List<EventSource> events) {
        List<EventSource> result = new UniqueList<>();
        for (EventSource event : events) {
            // Create a deep-copy of each EventSource.
            result.add(new EventSource(event));
        }
        // Return an unmodifiable list.
        return Collections.unmodifiableList(result);
    }

    /**
     * Get an immutable, deep copy list of tasks
     * Ensures that tasks are unique.
     * @return an immutable, deep copy list of tasks
     */
    private List<TaskSource> deepCopyTasks(List<TaskSource> tasks) {
        List<TaskSource> result = new UniqueList<>();
        for (TaskSource task : tasks) {
            // Create a deep-copy of each EventSource.
            result.add(new TaskSource(task));
        }
        // Return an unmodifiable list.
        return Collections.unmodifiableList(result);
    }
}
