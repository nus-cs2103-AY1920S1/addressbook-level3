package seedu.address.model.undo;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.events.EventSource;

/**
 * Represents a snapshot of the ModelManager's events.
 */
public class UndoRedoState {
    private final List<EventSource> events;

    UndoRedoState() {
        this.events = new ArrayList<>();
    }

    UndoRedoState(List<EventSource> events) {
        this.events = events;
    }

    public List<EventSource> getEvents() {
        return events;
    }
}
