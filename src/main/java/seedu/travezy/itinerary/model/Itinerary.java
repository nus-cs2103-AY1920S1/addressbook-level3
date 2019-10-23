package seedu.travezy.itinerary.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.collections.ObservableList;

import seedu.travezy.itinerary.model.event.Event;
import seedu.travezy.itinerary.model.exceptions.ItineraryException;

/**
 * Wraps all data at the itinerary level
 */
public class Itinerary {
    /**
     * ArrayList which stores all the events in the itinerary.
     */
    private final EventList eventList;

    private final ArrayList<EventList> savedList;
    private final ArrayList<String> actionList;

    public Itinerary() {
        this.eventList = new EventList();
        this.savedList = new ArrayList<>();
        this.actionList = new ArrayList<>();
    }

    public ObservableList<Event> getEventList() {
        return eventList.asUnmodifiableObservableList();
    }

    public ArrayList<EventList> getSavedList() {
        return savedList;
    }

    ArrayList<String> getActionList() {
        return actionList;
    }

    void addAction(String commandText) {
        actionList.add(commandText);
    }

    void resetPointer() {
    }

    void addEvent(Event event) {
        eventList.addEvent(event);
    }

    void deleteEvent(int index) {
        eventList.deleteEvent(index);
    }

    void doneEvent(Event target, Event doneEvent) {
        eventList.doneEvent(target, doneEvent);
    }

    /**
     * Check whether the event list in the itinerary contain the specified event.
     * @param editedEvent the newly created event with the fields changed.
     * @return a boolean whether the event exists in the event list of the itinerary.
     */
    boolean hasEvent(Event editedEvent) {
        requireNonNull(editedEvent);
        return eventList.contains(editedEvent);
    }

    void setEvent(Event eventToEdit, Event editedEvent) throws ItineraryException {
        requireNonNull(editedEvent);

        eventList.setEvent(eventToEdit, editedEvent);
    }
}
