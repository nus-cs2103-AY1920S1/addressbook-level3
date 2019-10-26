package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.UniqueEventList;

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
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEvents newData) {
        requireNonNull(newData);

        setEvents(newData.getEvents());
    }

    //// event-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addEvent(Event e) {
        events.add(e);
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
