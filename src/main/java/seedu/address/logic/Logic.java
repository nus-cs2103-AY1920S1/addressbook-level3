package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.autocomplete.AutoCompleter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.common.ReferenceIdResolver;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;
import seedu.address.model.queue.Room;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates AutoCompleter to the current command text.
     *
     * @param commandText The command as entered by the user.
     */
    AutoCompleter updateAutoCompleter(String commandText);

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the a resolver class for mapping {@code ReferenceId} to {@code Person}.
     */
    ReferenceIdResolver getReferenceIdResolver();

    /**
     * Returns an unmodifiable view of the filtered list of patients
     */
    ObservableList<Person> getFilteredPatientList();

    /**
     * Returns an unmodifiable view of the filtered list of rooms
     */
    ObservableList<Room> getFilteredRoomList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the Appointment Book.
     *
     * @see Model#getAppointmentBook()
     */
    ReadOnlyAppointmentBook getAppointmentBook();

    /**
     * Returns the user prefs' appointment book file path.
     */
    Path getAppointmentBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
