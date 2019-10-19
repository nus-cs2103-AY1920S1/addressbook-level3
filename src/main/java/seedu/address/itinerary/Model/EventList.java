package seedu.address.itinerary.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.itinerary.model.Event.Event;
import seedu.address.itinerary.model.Exceptions.ItineraryException;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


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

    public void deleteEvent(int index) {
        events.remove(index - 1);
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

    public void doneEvent(Event target, Event doneEvent) {
        requireAllNonNull(target, doneEvent);

        int index = events.indexOf(target);

        events.set(index, doneEvent);
    }

    public boolean contains(Event editedEvent) {
        return events.contains(editedEvent);
    }

    public void setEvent(Event eventToEdit, Event editedEvent) throws ItineraryException {
        requireAllNonNull(eventToEdit, editedEvent);

        int index = events.indexOf(eventToEdit);
        if (index == -1) {
            throw new ItineraryException("Easy Pal! The index that you gave is not valid. O_o");
        }

        if (!eventToEdit.isSameEvent(editedEvent) && contains(editedEvent)) {
            throw new ItineraryException("Woah! Do at least try to make a single character change to your event! ;)");
        }

        events.set(index, editedEvent);
    }
}