package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.CalendarCompatibleRecord;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.performance.UniqueEventList;
import seedu.address.model.person.Person;

/**
 * Wraps Performance-related data at an EventList level.
 */
public class Performance implements ReadOnlyPerformance {

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

    public Performance() {}

    /**
     * Creates a Performance using the Events in the {@code toBeCopied}
     */
    public Performance(ReadOnlyPerformance toBeCopied) {
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
    public void resetData(ReadOnlyPerformance newData) {
        requireNonNull(newData);

        setEvents(newData.getPerformance());
    }

    //// event-level operations
    /**
     * Retrieves Calendar-compatible records for all events.
     */
    public HashMap<Event, List<CalendarCompatibleRecord>> getCalendarCompatiblePerformance(AthletickDate date) {
        requireNonNull(date);
        return events.getCalendarCompatiblePerformance(date);
    }

    public ArrayList<Event> getAthleteEvent(Person athlete) {
        requireNonNull(athlete);
        return events.getAthleteEvent(athlete);
    }

    /**
     * Checks if there are any recorded performances on a specified date.
     */
    public boolean hasPerformanceOn(AthletickDate date) {
        requireNonNull(date);
        return events.hasPerformanceOn(date);
    }

    /**
     * Returns true if an event with the same name as {@code event} exists in the events list.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Retrieves an instance of an event by the event name.
     */
    public Event getEvent(String eventName) {
        requireNonNull(eventName);
        return events.getEvent(eventName);
    }

    /**
     * Adds an event to the events list.
     * The event must not already exist in the events list.
     */
    public void addEvent(Event e) {
        requireNonNull(e);
        events.add(e);
    }

    /**
     * Removes an event from the list.
     * The event must already exist in the events list.
     */
    public void removeEvent(Event e) {
        requireNonNull(e);
        events.remove(e);
    }

    /**
     * Adds a performance record under a specific event.
     * @param e Event name of event where performance is recorded under.
     * @param p Person who completed the performance record.
     * @param r Record to be added.
     */
    public void addRecord(String e, Person p, Record r) {
        requireAllNonNull(e, p, r);
        events.getEvent(e).addRecord(p, r);
    }

    /**
     * Removes an athlete's record for a certain event, on a certain day.
     */
    public void deleteRecord(String e, Person p, AthletickDate d) {
        requireAllNonNull(e, p, d);
        events.getEvent(e).deleteRecord(p, d);
    }

    /**
     * Edits an athlete's record details accordingly when the EditCommand is executed.
     */
    public void editPersonPerformanceRecords(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        events.updatePerson(target, editedPerson);
    }

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
    }

    @Override
    public ObservableList<Event> getPerformance() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Performance // instanceof handles nulls
                && events.equals(((Performance) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }

}
