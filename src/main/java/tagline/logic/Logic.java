package tagline.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.ReadOnlyAddressBook;
import tagline.model.contact.Contact;

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
     * @see tagline.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of contacts */
    ObservableList<Contact> getFilteredContactList();

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
}
