package seedu.planner.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Name;
//@@author OneArmyj
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

    /**
     * Returns the end date time of the planner.
     */
    LocalDateTime getLastDateTime();

    /**
     * Returns the start date property of the planner.
     */
    SimpleObjectProperty<LocalDate> getStartDateProperty();

    /**
     * Returns the name property of the planner.
     */
    SimpleObjectProperty<Name> getNameProperty();
}
