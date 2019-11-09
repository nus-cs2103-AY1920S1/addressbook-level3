package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;
import seedu.address.model.person.Person;

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
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    //@@author shaoyi1997
    /** Returns an unmodifiable view of the filtered list of workers*/
    ObservableList<Worker> getFilteredWorkerList();

    /** Returns an unmodifiable view of the filtered list of bodies */
    ObservableList<Body> getFilteredBodyList();

    /** Returns an unmodifiable view of the filtered list of bodies */
    ObservableList<Fridge> getFilteredFridgeList();

    /** Returns an unmodifiable view of the filtered list of notifs */
    ObservableList<Notif> getFilteredNotifList();
    //@@author

    /** Returns an unmodifiable view of the filtered list of active notifs */
    ObservableList<Notif> getFilteredActiveNotifList();

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

    //@@author shaoyi1997-reused
    //Reused from SE-EDU Address Book Level 4
    /**
     * Selected body in the filtered body list.
     * null if no body is selected.
     *
     * @see seedu.address.model.Model#selectedBodyProperty()
     */
    ReadOnlyProperty<Body> selectedBodyProperty();

    /**
     * Sets the selected person in the filtered body list.
     *
     * @see seedu.address.model.Model#setSelectedBody(Body)
     */
    void setSelectedBody(Body body);
    //@@author
}
