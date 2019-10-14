package seedu.address.logic;

import java.nio.file.Path;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.TimeBook;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplay;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

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

    /**
     * Returns the TimeBook.
     */
    TimeBook getTimeBook();

    //=========== UI Model =============================================================

    /**
     * Returns the current main window display model.
     */
    DetailWindowDisplay getMainWindowDisplay();

    /**
     * Returns the current side panel display model.
     */
    SidePanelDisplay getSidePanelDisplay();

    //=========== Suggesters =============================================================

    /**
     * Returns a list of Person's names that starts with prefix.
     */
    ArrayList<String> personSuggester(String prefix);

    /**
     * Returns a list of Person's names that starts with prefix in a Group.
     */
    ArrayList<String> personSuggester(String prefix, String groupName);

    /**
     * Returns a list of Group's names that starts with prefix.
     */
    ArrayList<String> groupSuggester(String prefix);

    //=========== Legacy =============================================================

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
     * Returns an unmodifiable view of the list of groups.
     */
    ObservableList<Group> getGroupList();

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

    ObservableList<PersonDisplay> getFilteredPersonDisplayList();
}
