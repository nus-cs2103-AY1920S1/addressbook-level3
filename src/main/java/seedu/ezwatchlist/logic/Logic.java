package seedu.ezwatchlist.logic;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.ui.MainWindow;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @param mainWindow
     * @param currentTab
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText, MainWindow mainWindow, String currentTab)
            throws CommandException, ParseException, OnlineConnectionException, InterruptedException;

    /**
     * Returns the model.
     * @return the model
     */
    Model getModel();

    /**
     * Returns the WatchList.
     *
     * @see Model#getWatchList()
     */
    ReadOnlyWatchList getWatchList();

    /**
     * Returns the database of shows.
     *
     * @see Model#getDatabase()
     */
    ReadOnlyWatchList getDatabase();

    /** Returns an unmodifiable view of the filtered shows that have not been watched */
    ObservableList<Show> getUnWatchedList();

    /** Returns an unmodifiable view of the filtered watched list of shows */
    ObservableList<Show> getWatchedList();

    /** Returns an unmodifiable view of the filtered list of shows */
    ObservableList<Show> getFilteredShowList();

    /** Updates the filter of the filtered show list by the given {@code predicate}. */
    void updateFilteredShowList(Predicate<Show> predicate);

    /** Returns an unmodifiable view of the search results of shows */
    ObservableList<Show> getSearchResultList();

    /**
     * Returns the user prefs' watchlist file path.
     */
    Path getWatchListFilePath();

    /**
     * Returns the user prefs' database file path.
     */
    Path getDatabaseFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
