package seedu.address.itinerary.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.MonthData;
import seedu.address.commons.util.StatisticsUtil;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.exceptions.ItineraryException;

/**
 * Access the filtered event list of the itinerary.
 */
public class Model {
    public static final Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    private Itinerary itinerary;
    private final FilteredList<Event> filteredEvents;
    private final SortedList<Event> sortedEvents;

    public Model() {
        this.itinerary = new Itinerary();
        filteredEvents = new FilteredList<>(this.itinerary.getEventList());
        sortedEvents = new SortedList<>(filteredEvents);
    }

    public void addEvent(Event event) {
        this.itinerary.addEvent(event);
    }

    public void deleteEvent(Event event) {
        this.itinerary.deleteEvent(event);
    }

    public void setEvent(Event eventToEdit, Event editedEvent) throws ItineraryException {
        CollectionUtil.requireAllNonNull(eventToEdit, editedEvent);

        itinerary.setEvent(eventToEdit, editedEvent);
    }

    public ArrayList<String> getActionList() {
        return itinerary.getActionList();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Expense}
     * @return
     */
    public SortedList<Event> getSortedEventList() {
        return sortedEvents;
    }

    /**
     * Filter out the events in the event list base on the predicate.
     * @param predicate the condition use to filter out the events.
     */
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    public void setItinerary(ReadOnlyItinerary readOnlyItinerary) {
        this.itinerary.resetData(readOnlyItinerary);
    }

    public ReadOnlyItinerary getItinerary() {
        return itinerary;
    }

    /**
     * Mark the specified event in the itinerary event list as done.
     * @param target the specified event to be marked done.
     * @param doneEvent the event with the attribute mark done.
     */
    public void doneEvent(Event target, Event doneEvent) {
        CollectionUtil.requireAllNonNull(target, doneEvent);

        itinerary.doneEvent(target, doneEvent);
    }

    /**
     * Check whether the event list in the itinerary contain the specified event.
     * @param editedEvent the newly created event with the fields changed.
     * @return a boolean whether the event exists in the event list of the itinerary.
     */
    public boolean hasEvent(Event editedEvent) {
        requireNonNull(editedEvent);
        return itinerary.hasEvent(editedEvent);
    }

    /**
     * Add the current action call into the action list.
     * @param commandText the command input that is given by the user.
     */
    public void addAction(String commandText) {
        requireNonNull(commandText);

        itinerary.addAction(commandText);
        itinerary.resetPointer();
    }

    public void clearEvent() {
        itinerary.clear();
    }

    public int getTotalItineraryEntries() {
        return filteredEvents.size();
    }

    public XYChart.Series<String, Number> getItineraryBarChart() {
        Function<Event, MonthData> toMonthDataFunction = event -> new MonthData(0, event.getMonthAndYear());
        return StatisticsUtil.getMonthDataSeries(filteredEvents, toMonthDataFunction);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Model // instanceof handles nulls
                && sortedEvents.equals(((Model) other).sortedEvents));
    }
}
