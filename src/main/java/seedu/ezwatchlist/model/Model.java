package seedu.ezwatchlist.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Show> PREDICATE_ALL_SHOWS = unused -> true;

    /** {@code Predicate} that evaluates to true when a show is not watched*/
    Predicate<Show> PREDICATE_UNWATCHED_SHOWS = show -> !show.isWatched().value;

    /** {@code Predicate} that evaluates to true when a show is watched */
    Predicate<Show> PREDICATE_WATCHED_SHOWS = show -> show.isWatched().value;

    /** {@code Predicate} that always evaluates to false */
    Predicate<Show> PREDICATE_NO_SHOWS = unused -> false;

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

    /**
     * Returns the WatchList
     */
    ReadOnlyWatchList getWatchList();

    /**
     * Returns true if a show with the same identity as {@code show} exists in the watchlist.
     * @param show The show to be checked.
     * @return True if a same show exists in the watchlist.
     */
    boolean hasShow(Show show);

    /**
     * Returns true if a show with the same name as {@code showName} exists in the watchlist.
     * @param showName The name of the show to be searched.
     * @return True if a show with the same name exists in the watchlist.
     */
    boolean hasShowName(Name showName);

    /**
     * Returns the list of shows that has the same name as the given argument as the current watch list.
     * @param showName The name of the show to be searched.
     * @return The list of shows that has the same name exists in the watchlist.
     */
    List<Show> getShowIfHasName(Name showName);

    /**
     * Returns true if a show with any of the actor in {@code actorSet} exists in the watchlist.
     * @param actorSet The set of actors to be searched in the watchlist.
     * @return True if a show with any actor in the {@code actorSet} exists in the watchlist.
     */
    boolean hasActor(Set<Actor> actorSet);

    /**
     * Returns the list of shows that has any of the actor in {@code actorSet}.
     * @param actorSet The set of actors to be searched in the watchlist.
     * @return The list of shows that has any of the actor in {@code actorSet}.
     */
    List<Show> getShowIfHasActor(Set<Actor> actorSet);

    /**
     * Returns the list of shows that has any of the genre in {@code genreSet}.
     * @param genreSet The set of genres to be searched in the watchlist.
     * @return The list of shows that has any of the genre in {@code genreSet}.
     */
    List<Show> getShowIfIsGenre(Set<Genre> genreSet);

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

    /** Returns an unmodifiable view of the unwatched show list */
    ObservableList<Show> getUnWatchedShowList();

    /** Returns an unmodifiable view of the watched show list */
    ObservableList<Show> getWatchedShowList();

    /**
     * Updates the unwatched show list to filter shows that have not been watched.
     */
    void updateUnWatchedShowList();

    /**
     * Updates the watched show list to filter shows that have been watched.
     */
    void updateWatchedShowList();

    /** Returns an unmodifiable view of the search result list */
    ObservableList<Show> getSearchResultList();

    /**
     * Updates the filter of the filtered show list to filter by the given {@code searchResult}.
     * @throws NullPointerException if the {@code searchResult} if null.
     */
    void updateSearchResultList(List<Show> searchResult);

    /**
     * Retrieve a list of Movies from unwatchedlist.
     * @throws NullPointerException if the {@code searchResult} if null.
     */
    ObservableList<Show> getMoviesFromUnWatchedList();

    /**
     * Returns a string representing the name of the page.
     */
    String getPage(String shortCutKey);
}
