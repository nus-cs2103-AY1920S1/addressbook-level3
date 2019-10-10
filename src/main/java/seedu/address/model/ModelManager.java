package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.show.Show;

/**
 * Represents the in-memory model of the watchlist data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final WatchList watchList;
    private final UserPrefs userPrefs;
    private final FilteredList<Show> filteredShows;

    /**
     * Initializes a ModelManager with the given watchList and userPrefs.
     */
    public ModelManager(ReadOnlyWatchList watchList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(watchList, userPrefs);

        logger.fine("Initializing with watchlist: " + watchList + " and user prefs " + userPrefs);

        this.watchList = new WatchList(watchList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredShows = new FilteredList<>(this.watchList.getShowList());
    }

    public ModelManager() {
        this(new WatchList(), new UserPrefs());
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
    public void deleteShow(Show target) {
        watchList.removeShow(target);
    }

    @Override
    public void addShow(Show show) {
        watchList.addShow(show);
        updateFilteredShowList(PREDICATE_SHOW_ALL_SHOWS);
    }

    @Override
    public void setShow(Show target, Show editedShow) {
        requireAllNonNull(target, editedShow);

        watchList.setShow(target, editedShow);
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
                && userPrefs.equals(other.userPrefs)
                && filteredShows.equals(other.filteredShows);
    }

}
