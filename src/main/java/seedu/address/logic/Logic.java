package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyExpiryDateTracker;
import seedu.address.model.item.Item;

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
     * Returns the ExpiryDateTracker.
     *
     * @see seedu.address.model.Model#getExpiryDateTracker()
     */
    ReadOnlyExpiryDateTracker getExpiryDateTracker();

    /** Returns an unmodifiable view of the filtered list of items */
    ObservableList<Item> getFilteredItemList();

    /**
     * Returns the user prefs' expiry date tracker file path.
     */
    Path getExpiryDateTrackerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
