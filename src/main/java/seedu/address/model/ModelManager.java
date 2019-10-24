package seedu.address.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.model.events.EventSource;
import seedu.address.model.listeners.EventListListener;
import seedu.address.model.listeners.UndoRedoListener;
import seedu.address.model.undo.UndoRedoState;

/**
 * Represents the in-memory model of Horo.
 */
public class ModelManager implements UndoRedoListener {
    private final NotNullList<EventSource> eventList;
    private final List<EventListListener> eventListListeners;

    /**
     * Creates a ModelManager.
     */
    public ModelManager() {
        super();
        this.eventList = new NotNullList<>();
        this.eventListListeners = new ArrayList<>();
    }

    public void addEventListListener(EventListListener listener) {
        this.eventListListeners.add(listener);
    }

    /* Events */

    /**
     * Adds EventSource(s) to this model's eventList.
     * @param events the EventSource(s) to add
     */
    public void addEvents(EventSource... events) {
        for (EventSource e : events) {
            // Create a deep-copy of each addition.
            this.eventList.add(new EventSource(e));
        }
        notifyEventListListeners();
    }

    public boolean containsEvent(EventSource event) {
        return this.eventList.contains(event);
    }

    /**
     * Removes an EventSource from this model.
     * @param event the EventSource to remove.
     */
    public void removeEvent(EventSource event) {
        this.eventList.remove(event);
        notifyEventListListeners();
    }

    /**
     * Replaces an EventSource in this model with another EventSource.
     * @param event the EventSource to replace
     * @param replacement the replacement
     */
    public void replaceEvent(EventSource event, EventSource replacement) {
        // Create a deep-copy of the replacement.
        this.eventList.replace(event, new EventSource(replacement));
        notifyEventListListeners();
    }

    /**
     * Replaces the entire EventList in this model with a list of events.
     * @param events the events to replace the entire EventList.
     */
    public void setEventList(List<EventSource> events) {
        this.eventList.reset(events);
        notifyEventListListeners();
    }

    /**
     * Returns an unmodifiable, deep copy of this model's EventList.
     * @return a copy of the EventList
     */
    public List<EventSource> getEventList() {
        List<EventSource> result = new ArrayList<>();
        for (EventSource event : this.eventList) {
            // Create a deep-copy of each EventSource.
            result.add(new EventSource(event));
        }
        // Return an unmodifiable list.
        return Collections.unmodifiableList(result);
    }

    /**
     * Notify all listeners whenever the EventList is changed.
     */
    private void notifyEventListListeners() {
        this.eventListListeners.forEach(listener -> listener.onEventListChange(this.getEventList()));
    }

    @Override
    public void onUndoRedo(UndoRedoState state) {
        this.setEventList(state.getEvents());
    }
}
