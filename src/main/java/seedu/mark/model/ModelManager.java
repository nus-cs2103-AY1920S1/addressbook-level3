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
 * Represents the in-memory model of the bookmark manager data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final BookmarkManager bookmarkManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Bookmark> filteredBookmarks;

    /**
     * Initializes a ModelManager with the given bookmarkManager and userPrefs.
     */
    public ModelManager(ReadOnlyBookmarkManager bookmarkManager, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(bookmarkManager, userPrefs);

        logger.fine("Initializing with bookmark manager: " + bookmarkManager + " and user prefs " + userPrefs);

        this.bookmarkManager = new BookmarkManager(bookmarkManager);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBookmarks = new FilteredList<>(this.bookmarkManager.getBookmarkList());
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
    public void setBookmarkManager(ReadOnlyBookmarkManager bookmarkManager) {
        this.bookmarkManager.resetData(bookmarkManager);
    }

    @Override
    public ReadOnlyBookmarkManager getBookmarkManager() {
        return bookmarkManager;
    }

    @Override
    public boolean hasBookmark(Bookmark bookmark) {
        requireNonNull(bookmark);
        return bookmarkManager.hasBookmark(bookmark);
    }

    @Override
    public void deleteBookmark(Bookmark target) {
        bookmarkManager.removeBookmark(target);
    }

    @Override
    public void addBookmark(Bookmark bookmark) {
        bookmarkManager.addBookmark(bookmark);
        updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
    }

    @Override
    public void setBookmark(Bookmark target, Bookmark editedBookmark) {
        requireAllNonNull(target, editedBookmark);

        bookmarkManager.setBookmark(target, editedBookmark);
    }

    //=========== Filtered Bookmark List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Bookmark} backed by the internal list of
     * {@code versionedBookmarkManager}
     */
    @Override
    public ObservableList<Bookmark> getFilteredBookmarkList() {
        return filteredBookmarks;
    }

    @Override
    public void updateFilteredBookmarkList(Predicate<Bookmark> predicate) {
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
        return bookmarkManager.equals(other.bookmarkManager)
                && userPrefs.equals(other.userPrefs)
                && filteredBookmarks.equals(other.filteredBookmarks);
    }

}
