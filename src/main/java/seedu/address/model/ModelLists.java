package seedu.address.model;

import java.util.List;
import java.util.Objects;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

/**
 * Represents a snapshot of the ModelManager's lists.
 */
public class ModelLists {
    private final List<EventSource> events;
    private final List<TaskSource> tasks;

    public ModelLists(List<EventSource> events, List<TaskSource> tasks) {
        this.events = Objects.requireNonNull(events);
        this.tasks = Objects.requireNonNull(tasks);
    }

    public List<EventSource> getEvents() {
        return events;
    }

    public List<TaskSource> getTasks() {
        return tasks;
    }

}
