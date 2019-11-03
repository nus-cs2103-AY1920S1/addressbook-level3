package seedu.address.itinerary.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.exceptions.ItineraryException;


/**
 * Wraps all data at the itinerary level
 */
public class Itinerary implements ReadOnlyItinerary {
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

    public Itinerary(List<Event> events) {
        this();
        eventList.setEvents(events);
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

    void deleteEvent(Event event) {
        eventList.deleteEvent(event);
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

    /**
     * Update the json file of the itinerary by writing the data on the json file after a command which edits and
     * changes parts of the event list in the itinerary.
     * @param readOnlyItinerary readable itinerary which prevents editing of data.
     */
    public void updateItinerary(ReadOnlyItinerary readOnlyItinerary) {
        eventList.clear();
        List<Event> eventList = readOnlyItinerary.getEventList();
        for (Event event : eventList) {
            this.eventList.addEvent(event);
        }
    }

    public void clear() {
        eventList.clear();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyItinerary newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
    }

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEvents(List<Event> events) {
        this.eventList.setEvents(events);
    }
}
