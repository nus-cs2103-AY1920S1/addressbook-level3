package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Review;
import seedu.address.model.feed.Feed;
import seedu.address.model.statistics.Statistics;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FeedList feedList;
    private final UserPrefs userPrefs;

    private FilteredList<Eatery> filteredTodo;
    private FilteredList<Eatery> filteredEateries;
    private Statistics stats;
    private ObservableList<Review> activeReviews;
    private Eatery activeEatery;

    /**
     * Initializes a ModelManager with the given addressBook, feedList and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyFeedList feedList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, feedList, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + ", feed list: " + feedList
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.feedList = new FeedList(feedList);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredEateries = new FilteredList<>(this.addressBook.getEateryList());
        filteredTodo = new FilteredList<>(this.addressBook.getTodoList());
        activeReviews = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new AddressBook(), new FeedList(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
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

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasEatery(Eatery eatery) {
        requireNonNull(eatery);
        return addressBook.hasEatery(eatery);
    }

    @Override
    public void deleteEatery(Eatery target) {
        addressBook.removeEatery(target);
    }

    @Override
    public void addEatery(Eatery eatery) {
        addressBook.addEatery(eatery);
        updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);
    }

    @Override
    public void setEatery(Eatery target, Eatery editedEatery) {
        requireAllNonNull(target, editedEatery);
        addressBook.setEatery(target, editedEatery);
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
     * {@code versionedAddressBook}
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
        if (addressBook.isMainMode()) {
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
        addressBook.toggle();
    }

    @Override
    public boolean isMainMode() {
        return addressBook.isMainMode();
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
        return addressBook.equals(other.addressBook)
                && feedList.equals(other.feedList)
                && userPrefs.equals(other.userPrefs)
                && filteredEateries.equals(other.filteredEateries);
    }

}
