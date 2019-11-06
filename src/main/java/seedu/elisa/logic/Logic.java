package seedu.elisa.logic;

import java.nio.file.Path;

import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import seedu.elisa.commons.core.GuiSettings;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.logic.commands.CommandResult;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.ItemStorage;
import seedu.elisa.model.PriorityExitStatus;
import seedu.elisa.model.item.VisualizeList;

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
     * Returns the AddressBook.
     *
     * @see seedu.elisa.model.Model#getAddressBook()
     */
    ItemStorage getItemStorage();

    /*
    /** Returns an unmodifiable view of the filtered list of persons
    ObservableList<Person> getFilteredPersonList();
    */

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

    //ObservableList<Person> getFilteredPersonList();
    VisualizeList getVisualList();

    ItemModel getModel();
    //Bryan Reminder
    void shutdown();

    ListPropertyBase<Item> getActiveRemindersListProperty();

    SimpleBooleanProperty getPriorityMode();

    boolean isSystemToggle();

    PriorityExitStatus getExitStatus();
}
