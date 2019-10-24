package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result. This method is used to differentiate between
     * an invalid merge command input by the user and a system called merge command.
     *
     * @param commandText   The command as entered by the user.
     * @param isSystemInput Whether the command was invoked by user or the program.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText, boolean isSystemInput) throws CommandException, ParseException;

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered list of policies
     */
    ObservableList<Policy> getFilteredPolicyList();

    /**
     * Returns an unmodifiable view of the filtered list of BinItems
     */
    ObservableList<BinItem > getFilteredBinItemList();

    /**
     * Returns an unmodifiable view of the previously entered commands
     */
    ObservableList<Pair<String, String>> getHistoryList();

    /**
     * Returns key-value mapping of policy type to number of people who bought that policy.
     */
    public ObservableMap<String, Integer> getPolicyPopularityBreakdown();

    /**
     * Returns key-value mapping of age group to number of people in the group.
     */
    public ObservableMap<String, Integer> getAgeGroupBreakdown();

    /**
     * Returns key-value mapping of gender to number of people of that gender.
     */
    public ObservableMap<String, Integer> getGenderBreakdown();

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
     * Returns the user prefs' settings.
     */
    UserSettings getUserSettings();

    /**
     * Set the user prefs' settings specified through commands.
     */
    void setUserSettings();

    /**
     * Set the user prefs' settings.
     */
    void setUserSettings(UserSettings userSettings);
}
