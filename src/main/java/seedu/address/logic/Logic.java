package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.events.exceptions.EventException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAccommodation;
import seedu.address.model.ReadOnlyActivity;
import seedu.address.model.ReadOnlyContact;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.contact.Contact;
import seedu.address.model.day.Day;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, EventException;

    /**
     * Returns the Accommodation.
     *
     * @see seedu.address.model.Model#getAccommodations()
     */
    ReadOnlyAccommodation getAccommodations();

    /** Returns an unmodifiable view of the filtered list of accommodations */
    ObservableList<Accommodation> getFilteredAccommodationList();

    /**
     * Returns the user prefs' accommodation file path.
     */
    Path getAccommodationFilePath();

    /**
     * Returns the Activity.
     *
     * @see seedu.address.model.Model#getActivities()
     */
    ReadOnlyActivity getActivities();

    /** Returns an unmodifiable view of the filtered list of activities */
    ObservableList<Activity> getFilteredActivityList();

    /**
     * Returns the user prefs' activity file path.
     */
    Path getActivityFilePath();

    /**
     * Returns the Contact.
     *
     * @see seedu.address.model.Model#getContacts()
     */
    ReadOnlyContact getContacts();

    /** Returns an unmodifiable view of the filtered list of contacts */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Returns the user prefs' contact file path.
     */
    Path getContactFilePath();

    /**
     * Returns the Itinerary.
     *
     * @see seedu.address.model.Model#getItinerary()
     */
    ReadOnlyItinerary getItinerary();

    /** Returns an unmodifiable view of the filtered itinerary */
    ObservableList<Day> getFilteredItinerary();

    /**
     * Returns the user prefs' itinerary file path.
     */
    Path getItineraryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
