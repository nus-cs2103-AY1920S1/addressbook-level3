package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.show.Show;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Show> PREDICATE_SHOW_ALL_SHOWS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' watchlist file path.
     */
    Path getWatchListFilePath();

    /**
     * Sets the user prefs' watchlist file path.
     */
    void setWatchListFilePath(Path watchListFilePath);

    /**
     * Replaces watchlist data with the data in {@code watchList}.
     */
    void setWatchList(ReadOnlyWatchList watchList);

    /** Returns the WatchList */
    ReadOnlyWatchList getWatchList();

    /**
     * Returns true if a show with the same identity as {@code show} exists in the watchlist.
     */
    boolean hasShow(Show show);

    /**
     * Deletes the given show.
     * The show must exist in the watchlist.
     */
    void deleteShow(Show target);

    /**
     * Adds the given show.
     * {@code show} must not already exist in the watchlist.
     */
    void addShow(Show show);

    /**
     * Replaces the given show {@code target} with {@code editedShow}.
     * {@code target} must exist in the watchlist.
     * The show identity of {@code editedShow} must not be the same as another existing show in the watchlist.
     */
    void setShow(Show target, Show editedShow);

    /** Returns an unmodifiable view of the filtered show list */
    ObservableList<Show> getFilteredShowList();

    /**
     * Updates the filter of the filtered show list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredShowList(Predicate<Show> predicate);
}
