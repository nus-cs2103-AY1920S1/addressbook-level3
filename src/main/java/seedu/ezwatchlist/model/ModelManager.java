package seedu.ezwatchlist.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.commons.util.CollectionUtil;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Genre;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Show;

/**
 * Represents the in-memory model of the watchlist data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WatchList watchList;
    private final WatchList database;
    private final WatchList searchResult = new WatchList();
    private final FilteredList<Show> unWatchedList;
    private final FilteredList<Show> watchedList;
    private final UserPrefs userPrefs;

    private FilteredList<Show> filteredShows;
    private final String[] pageResult = {"Watchlist", "Watchedlist", "Search", "Statistics"};


    /**
     * Initializes a ModelManager with the given watchList and userPrefs.
     */
    public ModelManager(ReadOnlyWatchList watchList, ReadOnlyWatchList database, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(watchList, userPrefs);

        logger.fine("Initializing with watchlist: " + watchList + ", database: " + database + " and user prefs "
                + userPrefs);

        this.watchList = new WatchList(watchList);
        this.database = new WatchList(database);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredShows = new FilteredList<>(this.watchList.getShowList());

        unWatchedList = new FilteredList<>(this.watchList.getShowList());
        updateUnWatchedShowList();
        watchedList = new FilteredList<>(this.watchList.getShowList());
        updateWatchedShowList();
    }

    public ModelManager() {
        this(new WatchList(), new WatchList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getWatchListFilePath() {
        return userPrefs.getWatchListFilePath();
    }

    @Override
    public void setWatchListFilePath(Path watchListFilePath) {
        requireNonNull(watchListFilePath);
        userPrefs.setWatchListFilePath(watchListFilePath);
    }

    //=========== WatchList ================================================================================

    @Override
    public void setWatchList(ReadOnlyWatchList watchList) {
        this.watchList.resetData(watchList);
    }

    @Override
    public ReadOnlyWatchList getWatchList() {
        return watchList;
    }

    @Override
    public boolean hasShow(Show show) {
        requireNonNull(show);
        return watchList.hasShow(show);
    }

    @Override
    public boolean hasShowName(Name showName) {
        requireNonNull(showName);
        return watchList.hasName(showName);
    }

    @Override
    public List<Show> getShowFromWatchlistIfHasName(Name showName) {
        requireNonNull(showName);
        return watchList.getShowIfHasName(showName);
    }

    @Override
    public boolean hasActor(Set<Actor> actorSet) {
        requireNonNull(actorSet);
        return watchList.hasActor(actorSet);
    }

    @Override
    public List<Show> getShowFromWatchlistIfHasActor(Set<Actor> actorSet) {
        requireNonNull(actorSet);
        return watchList.getShowIfHasActor(actorSet);
    }

    @Override
    public List<Show> getShowFromWatchlistIfIsGenre(Set<Genre> genreSet) {
        requireNonNull(genreSet);
        return watchList.getShowIfIsGenre(genreSet);
    }

    @Override
    public void deleteShow(Show target) {
        watchList.removeShow(target);
    }

    @Override
    public void addShow(Show show) {
        watchList.addShow(show);
    }

    @Override
    public void setShow(Show target, Show editedShow) {
        CollectionUtil.requireAllNonNull(target, editedShow);
        watchList.setShow(target, editedShow);
    }

    @Override
    public String getPage(String shortCutKey) {
        int pageNumber = Integer.parseInt(shortCutKey) - 1;
        return pageResult[pageNumber];
    }

    //=========== Filtered Show List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Show} backed by the internal list of
     * {@code versionedWatchList}
     */
    @Override
    public ObservableList<Show> getFilteredShowList() {
        return filteredShows;
    }

    @Override
    public void updateFilteredShowList(Predicate<Show> predicate) {
        requireNonNull(predicate);
        filteredShows.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the watched list of {@code Show} backed by the internal list of
     * {@code versionedWatchList}
     */
    @Override
    public ObservableList<Show> getUnWatchedShowList() {
        return unWatchedList;
    }

    /**
     * Returns an unmodifiable view of the watched list of {@code Show} backed by the internal list of
     * {@code versionedWatchList}
     */
    @Override
    public ObservableList<Show> getWatchedShowList() {
        return watchedList;
    }

    @Override
    public void updateUnWatchedShowList() {
        unWatchedList.setPredicate(Model.PREDICATE_UNWATCHED_SHOWS);
    }

    @Override
    public void updateWatchedShowList() {
        watchedList.setPredicate(Model.PREDICATE_WATCHED_SHOWS);
    }

    @Override
    public void updateSearchResultList(List<Show> shows) {
        searchResult.setShows(shows);
        updateFilteredShowList(PREDICATE_ALL_SHOWS);
    }

    public ObservableList<Show> getSearchResultList() {
        return searchResult.getShowList();
    }

    //=========== Database ================================================================================
    @Override
    public Path getDatabaseFilePath() {
        return userPrefs.getDatabaseFilePath();
    }

    @Override
    public void setDatabaseFilePath(Path databaseFilePath) {
        requireNonNull(databaseFilePath);
        userPrefs.setDatabaseFilePath(databaseFilePath);
    }

    @Override
    public void setDatabase(ReadOnlyWatchList database) {
        this.database.resetData(database);
    }

    @Override
    public ReadOnlyWatchList getDatabase() {
        return database;
    }

    @Override
    public List<Show> getShowFromDatabaseIfHasName(Name showName) {
        requireNonNull(showName);
        return database.getShowIfHasName(showName);
    }

    @Override
    public List<Show> getShowFromDatabaseIfHasActor(Set<Actor> actorSet) {
        requireNonNull(actorSet);
        return database.getShowIfHasActor(actorSet);
    }

    @Override
    public List<Show> getShowFromDatabaseIfIsGenre(Set<Genre> genreSet) {
        requireNonNull(genreSet);
        return database.getShowIfIsGenre(genreSet);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return watchList.equals(other.watchList)
                && watchedList.equals(other.watchedList)
                && database.equals(other.database)
                && userPrefs.equals(other.userPrefs)
                && filteredShows.equals(other.filteredShows);
    }

}
