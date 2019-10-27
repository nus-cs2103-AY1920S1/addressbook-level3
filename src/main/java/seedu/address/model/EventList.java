package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.performance.UniqueEventList;
import seedu.address.model.person.Person;

/**
 * Wraps Performance-related data at an EventList level.
 */
public class EventList implements ReadOnlyEvents {

    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new UniqueEventList();
    }

    public EventList() {}

    /**
     * Creates an EventList using the Events in the {@code toBeCopied}
     */
    public EventList(ReadOnlyEvents toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the events list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventList} with {@code newData}.
     */
    public void resetData(ReadOnlyEvents newData) {
        requireNonNull(newData);

        setEvents(newData.getEvents());
    }

    //// event-level operations

    /**
     * Returns true if an event with the same name as {@code event} exists in the events list.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds a person to the events list.
     * The event must not already exist in the events list.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Adds a performance record under a specific event.
     * @param e Event name of event where performance is recorded under.
     * @param p Person who completed the performance record.
     * @param r Record to be added.
     * @return Details of the performance record.
     */
    public String addRecord(String e, Person p, Record r) {
        return events.getEvent(e).addPerformance(p, r);
    }

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
    }

    @Override
    public ObservableList<Event> getEvents() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && events.equals(((EventList) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }

}
