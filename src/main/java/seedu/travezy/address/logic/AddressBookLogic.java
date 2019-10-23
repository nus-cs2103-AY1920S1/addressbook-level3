package seedu.travezy.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.travezy.commons.core.GuiSettings;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.logic.commands.exceptions.CommandException;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.address.model.AddressBookModel;
import seedu.travezy.address.model.ReadOnlyAddressBook;
import seedu.travezy.address.model.person.Person;

/**
 * API of the AddressBookLogic component
 */
public interface AddressBookLogic {
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
     * @see AddressBookModel#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

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
