package seedu.ezwatchlist.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;

import static java.util.Objects.requireNonNull;

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
     * Returns true if a show with the same name as {@code show} exists in the watchlist.
     */
    boolean hasShowName(Name showName);

    /**
     * Returns the list of shows that has the same name as the given argument as the current watch list.
     */
    List<Show> getShowIfSameNameAs(Name showName);

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

    /** Returns an unmodifiable view of the watched show list */
    ObservableList<Show> getWatchedShowList();

    /**
     * Updates the watched show list to filter by the given watched status.
     */
    void updateWatchedShowList();

    /** Returns an unmodifiable view of the search result list */
    ObservableList<Show> getSearchResultList();

    /**
     * Updates the filter of the filtered show list to filter by the given {@code searchResult}.
     * @throws NullPointerException if the {@code searchResult} if null.
     */
    public void updateSearchResultList(List<Show> searchResult);
}
