package seedu.ezwatchlist.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.show.Show;

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
     * Returns the WatchList.
     *
     * @see Model#getWatchList()
     */
    ReadOnlyWatchList getWatchList();

    /** Returns an unmodifiable view of the filtered list of shows */
    ObservableList<Show> getFilteredShowList();

    /**
     * Returns the user prefs' watchlist file path.
     */
    Path getWatchListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
