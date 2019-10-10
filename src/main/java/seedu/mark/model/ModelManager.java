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
import seedu.mark.model.bookmark.Folder;

/**
 * Represents the in-memory model of the Mark data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Mark mark;
    private final UserPrefs userPrefs;
    private final FilteredList<Bookmark> filteredBookmarks;

    /**
     * Initializes a ModelManager with the given mark and userPrefs.
     */
    public ModelManager(ReadOnlyMark mark, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(mark, userPrefs);

        logger.fine("Initializing with Mark: " + mark + " and user prefs " + userPrefs);

        this.mark = new Mark(mark);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBookmarks = new FilteredList<>(this.mark.getBookmarkList());
    }

    public ModelManager() {
        this(new Mark(), new UserPrefs());
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
    public Path getMarkFilePath() {
        return userPrefs.getMarkFilePath();
    }

    @Override
    public void setMarkFilePath(Path markFilePath) {
        requireNonNull(markFilePath);
        userPrefs.setMarkFilePath(markFilePath);
    }

    //=========== Mark ================================================================================

    @Override
    public void setMark(ReadOnlyMark mark) {
        this.mark.resetData(mark);
    }

    @Override
    public ReadOnlyMark getMark() {
        return mark;
    }

    @Override
    public boolean hasBookmark(Bookmark bookmark) {
        requireNonNull(bookmark);
        return mark.hasBookmark(bookmark);
    }

    @Override
    public void deleteBookmark(Bookmark target) {
        mark.removeBookmark(target);
    }

    @Override
    public void addBookmark(Bookmark bookmark) {
        mark.addBookmark(bookmark);
        updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
    }

    @Override
    public void setBookmark(Bookmark target, Bookmark editedBookmark) {
        requireAllNonNull(target, editedBookmark);

        mark.setBookmark(target, editedBookmark);
    }


    @Override
    public void addFolder(Folder folder, Folder parentFolder) {
        requireAllNonNull(folder, parentFolder);
        mark.addFolder(folder, parentFolder);
    }

    @Override
    public boolean hasFolder(Folder folder) {
        return mark.hasFolder(folder);
    }

    //=========== Filtered Bookmark List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Bookmark} backed by the internal list of
     * {@code versionedMark}
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
        return mark.equals(other.mark)
                && userPrefs.equals(other.userPrefs)
                && filteredBookmarks.equals(other.filteredBookmarks);
    }

}
