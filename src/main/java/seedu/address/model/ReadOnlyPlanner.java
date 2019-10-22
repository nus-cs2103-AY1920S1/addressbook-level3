package seedu.address.model;

import java.time.LocalDate;

import javafx.collections.ObservableList;

import seedu.address.model.contact.Contact;
import seedu.address.model.day.Day;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyPlanner {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Contact> getContactList();

    /**
     * Returns an unmodifiable view of the activities list.
     * This list will not contain any duplicate activities.
     */
    ObservableList<Activity> getActivityList();

    /**
     * Returns an unmodifiable view of the activities list.
     * This list will not contain any duplicate activities.
     */
    ObservableList<Accommodation> getAccommodationList();

    /**
     * Returns an unmodifiable view of the days list.
     * This list will not contain any duplicate days.
     */
    ObservableList<Day> getDayList();

    /**
     * Returns the name of the planner.
     */
    Name getName();

    /**
     * Returns the start date of the planner.
     */
    LocalDate getStartDate();

}
