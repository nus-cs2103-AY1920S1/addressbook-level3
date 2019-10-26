package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.performance.Event;

/**
 * Unmodifiable view of events
 */
public interface ReadOnlyEvents {

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEvents();

}
