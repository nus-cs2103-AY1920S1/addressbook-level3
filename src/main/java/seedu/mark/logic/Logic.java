package seedu.mark.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.logic.commands.CommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.bookmark.Bookmark;

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
     * Returns the BookmarkManager.
     *
     * @see seedu.mark.model.Model#getAddressBook()
     */
    ReadOnlyBookmarkManager getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Bookmark> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getBookmarkManagerFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
