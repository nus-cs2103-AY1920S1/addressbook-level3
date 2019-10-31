package seedu.address.itinerary.model;

import javafx.collections.ObservableList;
import seedu.address.itinerary.model.event.Event;

public interface ReadOnlyItinerary {
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    ObservableList<Event> getEventList();
}
