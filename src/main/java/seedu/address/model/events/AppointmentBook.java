//@@author SakuraBlossom
package seedu.address.model.events;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.ListIterator;

import javafx.collections.ObservableList;

import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.person.Person;

/**
 * Wraps all data at the appointment-book level
 * Duplicates are not allowed (by .isSameEvent comparison)
 */
public class AppointmentBook implements ReadOnlyAppointmentBook {

    private final UniqueEventList events;

    public AppointmentBook() {
        events = new UniqueEventList();
    }

    /**
     * Creates an PatientSchedule using the Events in the {@code toBeCopied}
     */
    public AppointmentBook(ReadOnlyAppointmentBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setAll(events);
    }

    /**
     * Resets the existing data of this {@code PatientSchedule} with {@code newData}.
     */
    public void resetData(ReadOnlyAppointmentBook newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
    }

    //// event-level operations

    /**
     * Returns true if a event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        return events.contains(event);
    }

    /**
     * Returns true if an exact {@code event} exists in the address book.
     */
    public boolean hasExactEvent(Event event) {
        return events.containsExact(event);
    }

    /**
     * Adds a event to the appointment book.
     * The event must not already exist in the appointment book an
     */
    public void addEvent(Event p) {
        events.add(p);
    }

    /**
     * Removes {@code key} from this {@code PatientSchedule}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    /**
     * Replaces the given person details of {@code target} with {@code editedPerson}.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatesPersonDetails(Person target, Person editedPerson) {
        events.updatesPersonDetails(target, editedPerson);
    }

    //// util methods

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public ListIterator<Event> getEventsInConflict(Event toCheck) {
        return events.getEventsInConflict(toCheck);
    }

    @Override
    public int countNumberOfEventsInConflict(Event event) {
        return events.countNumberOfEventsInConflict(event);
    }

    @Override
    public boolean allowedToSchedule(Event event, int maxNumberOfConcurrentEvents) {
        return allowedToSchedule(event, maxNumberOfConcurrentEvents);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentBook // instanceof handles nulls
                && events.equals(((AppointmentBook) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
