package seedu.address.itinerary.model;

import javafx.collections.ObservableList;
import seedu.address.itinerary.model.Event.Event;

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
}
