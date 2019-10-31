package seedu.address.itinerary.model;


import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.exceptions.ItineraryException;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * An event list for tracking events on itinerary.
 */
public class EventList {
    private final ObservableList<Event> events = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(events);

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    public int getSize() {
        return events.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Mark the specified event in the itinerary event list as done.
     * @param target the specified event to be marked done.
     * @param doneEvent the event with the attribute mark done.
     */
    public void doneEvent(Event target, Event doneEvent) {
        CollectionUtil.requireAllNonNull(target, doneEvent);

        int index = events.indexOf(target);

        events.set(index, doneEvent);
    }

    public boolean contains(Event editedEvent) {
        for (int i = 0; i < events.size(); i++ ) {
            Event currEvent = events.get(i);
            if (currEvent.isSameEvent(editedEvent)) {
                return true;
            }
        }
        return false;
    }

    public void setEvent(Event eventToEdit, Event editedEvent) throws ItineraryException {
        CollectionUtil.requireAllNonNull(eventToEdit, editedEvent);

        int index = events.indexOf(eventToEdit);
        if (index == -1) {
            throw new ItineraryException("Easy Pal! The index that you gave is not valid. O_o");
        }

        if (!eventToEdit.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new ItineraryException("Woah! Do at least try to make a single character change to your event! ;)");
        }

        events.set(index, editedEvent);
    }

    /**
     * Replaces the contents of this list with {@code events}.
     */
    public void setEvents(List<Event> events) {
        CollectionUtil.requireAllNonNull(events);

        this.events.setAll(events);
    }

    public void clear() {
        events.clear();
    }
}
