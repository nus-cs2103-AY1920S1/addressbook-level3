package seedu.address.itinerary.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.itinerary.model.Event.Event;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Model {
    /** {@code Predicate} that always evaluate to true */
    public Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    private Itinerary itinerary;
    private final FilteredList<Event> filteredEvents;

    public Model() {
        this.itinerary = new Itinerary();
        filteredEvents = new FilteredList<>(this.itinerary.getEventList());
    }

    /**
     * Returns an unmodifiable view of the list of {@code Expense}
     */
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    public void addEvent(Event event) {
        this.itinerary.addEvent(event);
    }

    public void deleteEvent(int index) {
        this.itinerary.deleteEvent(index);
    }

    public void doneEvent(Event target, Event doneEvent) {
        requireAllNonNull(target, doneEvent);

        itinerary.doneEvent(target, doneEvent);
    }
}