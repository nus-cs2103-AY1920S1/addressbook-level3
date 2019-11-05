//@@author SakuraBlossom
package seedu.address.model;

import java.util.ListIterator;

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

    /**
     * Returns a ListIterator of {@code Event} whose timing is in conflict with the given {@code event}.
     */
    public ListIterator<Event> getEventsInConflict(Event toCheck);

    /**
     * Returns the number of unique events which timings are in conflict
     * with the given {@code event}.
     */
    int countNumberOfEventsInConflict(Event event);

    /**
     * Returns true if the number of unique events which timings are in conflict
     * is lesser or equal to {@code maxNumberofConcurrentEvents} and the events in conflict does not
     * involve the same person given in {@code event}.
     */
    boolean allowedToSchedule(Event event, int maxNumberOfConcurrentEvents);

}
