package seedu.address.itinerary.model;

import javafx.collections.ObservableList;
import seedu.address.itinerary.model.Event.Event;
import seedu.address.itinerary.model.Exceptions.ItineraryException;

import static java.util.Objects.requireNonNull;

public class Itinerary {
    /**
     * ArrayList which stores all the events in the itinerary.
     */
    private final EventList eventList;

    public Itinerary() {
        this.eventList = new EventList();
    }

    public ObservableList<Event> getEventList() {
        return eventList.asUnmodifiableObservableList();
    }

    public void addEvent(Event event) {
        eventList.addEvent(event);
    }

    public void deleteEvent(int index) {
        eventList.deleteEvent(index);
    }

    public void doneEvent(Event target, Event doneEvent) {
        eventList.doneEvent(target, doneEvent);
    }

    public boolean hasEvent(Event editedEvent) {
        requireNonNull(editedEvent);
        return eventList.contains(editedEvent);
    }

    public void setEvent(Event eventToEdit, Event editedEvent) throws ItineraryException {
        requireNonNull(editedEvent);

        eventList.setEvent(eventToEdit, editedEvent);
    }
}