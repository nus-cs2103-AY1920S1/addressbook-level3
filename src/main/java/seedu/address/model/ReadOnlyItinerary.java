package seedu.address.model;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import seedu.address.model.day.Day;
import seedu.address.model.field.Name;

/**
 * Unmodifiable view of an Itinerary
 */
public interface ReadOnlyItinerary {

    /**
     * Returns an unmodifiable view of the itinerary.
     * This list will not contain any duplicate days.
     */
    ObservableList<Day> getItinerary();

    /**
     * Returns the name of the planner.
     */
    Name getName();

    /**
     * Returns the start date of the planner.
     */
    LocalDate getStartDate();
}
