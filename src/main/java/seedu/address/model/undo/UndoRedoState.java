package seedu.address.model.undo;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;

/**
 * Represents a snapshot of the ModelManager's events.
 */
public class UndoRedoState {
    private final List<EventSource> events;
    private final List<TaskSource> tasks;

    UndoRedoState() {
        this.events = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    UndoRedoState(List<EventSource> events, List<TaskSource> tasks) {
        this.events = events;
        this.tasks = tasks;
    }

    public List<EventSource> getEvents() {
        return events;
    }

    public List<TaskSource> getTasks() {
        return tasks;
    }

}
