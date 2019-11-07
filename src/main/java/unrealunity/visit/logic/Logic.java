package unrealunity.visit.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import unrealunity.visit.commons.core.GuiSettings;
import unrealunity.visit.logic.commands.Command;
import unrealunity.visit.logic.commands.CommandResult;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.logic.parser.exceptions.ParseException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.ReadOnlyAddressBook;
import unrealunity.visit.model.appointment.Appointment;
import unrealunity.visit.model.person.Person;

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

    CommandResult execute(Command command) throws CommandException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered list of appointments.
     */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Retrieves the available Reminders.
     */
    String outputReminders();
}
