package seedu.address.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.address.model.AddressBookModel;
import seedu.address.address.model.ReadOnlyAddressBook;
import seedu.address.address.model.person.Person;
import seedu.address.address.model.util.AddressBookStatistics;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

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
     * Get total number of persons from address book.
     */
    AddressBookStatistics getStatistics();

}
