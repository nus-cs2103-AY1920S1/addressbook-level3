package seedu.address.itinerary.model;

import javafx.collections.ObservableList;
import seedu.address.itinerary.model.event.Event;

/**
 * Interface for the read only itinerary as a defensive code to prevent editing of the event list.
 */
public interface ReadOnlyItinerary {
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    ObservableList<Event> getEventList();
}
