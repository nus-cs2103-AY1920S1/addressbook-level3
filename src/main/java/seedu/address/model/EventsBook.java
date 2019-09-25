package seedu.address.model;

import static java.util.Objects.requireNonNull;
import java.util.List;
import javafx.collections.ObservableList;
import seedu.address.model.events.Event;
import seedu.address.model.events.EventsList;

public class EventsBook implements ReadOnlyEventsBook {

    private final EventsList events;

    {
        events = new EventsList();
    }

    public EventsBook() {
    }

    public EventsBook(ReadOnlyEventsBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    public void resetData(ReadOnlyEventsBook newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
    }

    //// person-level operations

    public boolean hasPerson(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void setEvent(Event target, Event editedPerson) {
        requireNonNull(editedPerson);

        events.setEvent(target, editedPerson);
    }

    public void removePerson(Event key) {
        events.remove(key);
    }

    //// util methods

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }
}
