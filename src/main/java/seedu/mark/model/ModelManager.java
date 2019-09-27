package seedu.mark.model;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Bookmark;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BookmarkManager addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Bookmark> filteredBookmarks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyBookmarkManager addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new BookmarkManager(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBookmarks = new FilteredList<>(this.addressBook.getBookmarkList());
    }

    public ModelManager() {
        this(new BookmarkManager(), new UserPrefs());
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
    public Path getBookmarkManagerFilePath() {
        return userPrefs.getBookmarkManagerFilePath();
    }

    @Override
    public void setBookmarkManagerFilePath(Path bookmarkManagerFilePath) {
        requireNonNull(bookmarkManagerFilePath);
        userPrefs.setBookmarkManagerFilePath(bookmarkManagerFilePath);
    }

    //=========== BookmarkManager ================================================================================

    @Override
    public void setAddressBook(ReadOnlyBookmarkManager addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyBookmarkManager getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Bookmark bookmark) {
        requireNonNull(bookmark);
        return addressBook.hasBookmark(bookmark);
    }

    @Override
    public void deletePerson(Bookmark target) {
        addressBook.removeBookmark(target);
    }

    @Override
    public void addPerson(Bookmark bookmark) {
        addressBook.addBookmark(bookmark);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Bookmark target, Bookmark editedBookmark) {
        requireAllNonNull(target, editedBookmark);

        addressBook.setBookmark(target, editedBookmark);
    }

    //=========== Filtered Bookmark List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Bookmark} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Bookmark> getFilteredPersonList() {
        return filteredBookmarks;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Bookmark> predicate) {
        requireNonNull(predicate);
        filteredBookmarks.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredBookmarks.equals(other.filteredBookmarks);
    }

}
