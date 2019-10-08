package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.events.Event;

/**
 * Unmodifiable view of a roster schedule
 */
public interface ReadOnlyAppointmentBook {

    /**
     * Returns an unmodifiable view of the event list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

}
