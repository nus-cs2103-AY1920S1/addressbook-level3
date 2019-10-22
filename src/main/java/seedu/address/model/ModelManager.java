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
import seedu.address.model.eatery.Eatery;
import seedu.address.model.feed.Feed;

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
