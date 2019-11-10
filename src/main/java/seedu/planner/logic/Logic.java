package seedu.planner.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.planner.commons.core.GuiSettings;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.Day;
//@@author OneArmyj
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
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the Accommodation.
     *
     * @see seedu.planner.model.Model#getAccommodations()
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
     * @see seedu.planner.model.Model#getActivities()
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
     * @see seedu.planner.model.Model#getContacts()
     */
    ReadOnlyContact getContacts();

    /** Returns an unmodifiable view of the filtered list of contacts */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Returns the user prefs' contact file path.
     */
    Path getContactFilePath();

    /**
     * Returns the user prefs' planner file path.
     */
    Path getPlannerFilePath();

    /**
     * Returns the Itinerary.
     *
     * @see seedu.planner.model.Model#getItinerary()
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
