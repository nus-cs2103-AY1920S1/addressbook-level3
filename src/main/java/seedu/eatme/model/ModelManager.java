package seedu.eatme.model;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.eatme.commons.core.GuiSettings;
import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Review;
import seedu.eatme.model.feed.Feed;
import seedu.eatme.model.statistics.Statistics;

/**
 * Represents the in-memory model of the eatery list data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EateryList eateryList;
    private final FeedList feedList;
    private final UserPrefs userPrefs;

    private FilteredList<Eatery> filteredTodo;
    private FilteredList<Eatery> filteredEateries;
    private Statistics stats;
    private ObservableList<Review> activeReviews;
    private Eatery activeEatery;

    /**
     * Initializes a ModelManager with the given eateryList, feedList and userPrefs.
     */
    public ModelManager(ReadOnlyEateryList eateryList, ReadOnlyFeedList feedList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(eateryList, feedList, userPrefs);

        logger.fine("Initializing with eatery list: " + eateryList + ", feed list: " + feedList
                + " and user prefs " + userPrefs);

        this.eateryList = new EateryList(eateryList);
        this.feedList = new FeedList(feedList);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredEateries = new FilteredList<>(this.eateryList.getEateryList());
        filteredTodo = new FilteredList<>(this.eateryList.getTodoList());
        activeReviews = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new EateryList(), new FeedList(), new UserPrefs());
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
    public Path getEateryListFilePath() {
        return userPrefs.getEateryListFilePath();
    }

    @Override
    public void setEateryListFilePath(Path eateryListFilePath) {
        requireNonNull(eateryListFilePath);
        userPrefs.setEateryListFilePath(eateryListFilePath);
    }

    @Override
    public Path getFeedListFilePath() {
        return userPrefs.getFeedListFilePath();
    }

    @Override
    public void setFeedListFilePath(Path feedListFilePath) {
        requireNonNull(feedListFilePath);
        userPrefs.setFeedListFilePath(feedListFilePath);
    }

    //=========== EateryList ================================================================================

    @Override
    public void setEateryList(ReadOnlyEateryList eateryList) {
        this.eateryList.resetData(eateryList);
    }

    @Override
    public ReadOnlyEateryList getEateryList() {
        return eateryList;
    }

    @Override
    public boolean hasEatery(Eatery eatery) {
        requireNonNull(eatery);
        return eateryList.hasEatery(eatery);
    }

    @Override
    public void deleteEatery(Eatery target) {
        eateryList.removeEatery(target);
    }

    @Override
    public void addEatery(Eatery eatery) {
        eateryList.addEatery(eatery);
        updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);
    }

    @Override
    public void setEatery(Eatery target, Eatery editedEatery) {
        requireAllNonNull(target, editedEatery);
        eateryList.setEatery(target, editedEatery);
    }

    @Override
    public void setActiveEatery(Eatery eatery) {
        this.activeEatery = eatery;
    }

    @Override
    public Eatery getActiveEatery() {
        return this.activeEatery;
    }

    //=========== Filtered Eatery List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Eatery} backed by the internal list of
     * {@code versionedEateryList}
     */
    @Override
    public ObservableList<Eatery> getFilteredEateryList() {
        return filteredEateries;
    }

    @Override
    public ObservableList<Eatery> getFilteredTodoList() {
        return filteredTodo;
    }

    @Override
    public void updateFilteredEateryList(Predicate<Eatery> predicate) {
        requireNonNull(predicate);
        if (eateryList.isMainMode()) {
            filteredEateries.setPredicate(predicate);
        } else {
            filteredTodo.setPredicate(predicate);
        }
    }

    //=========== Active Review Accessors =============================================================

    /**
     * Returns an unmodifiable view of the active reviews
     */
    @Override
    public ObservableList<Review> getActiveReviews() {
        return activeReviews;
    }

    /**
     * Updates the list of active reviews based on the given {@code reviews}.
     */
    @Override
    public void updateActiveReviews(List<Review> reviews) {
        activeReviews.clear();
        activeReviews.addAll(reviews);
    }

    //=========== General =============================================================

    @Override
    public void toggle() {
        eateryList.toggle();
    }

    @Override
    public boolean isMainMode() {
        return eateryList.isMainMode();
    }

    //=========== FeedList ================================================================================

    @Override
    public void setFeedList(ReadOnlyFeedList feedList) {
        this.feedList.resetData(feedList);
    }

    @Override
    public ReadOnlyFeedList getFeedList() {
        return feedList;
    }

    @Override
    public boolean hasFeed(Feed feed) {
        requireNonNull(feed);
        return feedList.hasFeed(feed);
    }

    @Override
    public void deleteFeed(Feed target) {
        feedList.removeFeed(target);
    }

    @Override
    public void addFeed(Feed feed) {
        feedList.addFeed(feed);
    }

    @Override
    public void setFeed(Feed target, Feed editedFeed) {
        requireAllNonNull(target, editedFeed);

        feedList.setFeed(target, editedFeed);
    }

    //=========== Statistics ===============================================================================
    @Override
    public void setStatistics(Statistics stats) {
        this.stats = stats;
    }

    @Override
    public Statistics getStatistics() {
        return stats;
    }
    //=========== Utilities ================================================================================

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
        return eateryList.equals(other.eateryList)
                && feedList.equals(other.feedList)
                && userPrefs.equals(other.userPrefs)
                && filteredEateries.equals(other.filteredEateries);
    }

}
