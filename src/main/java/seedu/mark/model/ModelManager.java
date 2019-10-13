package seedu.mark.model;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Reminder;

/**
 * Represents the in-memory model of the Mark data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedMark versionedMark;
    private final UserPrefs userPrefs;
    private final FilteredList<Bookmark> filteredBookmarks;
    private final SimpleObjectProperty<Url> currentUrl = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given mark and userPrefs.
     */
    public ModelManager(ReadOnlyMark mark, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(mark, userPrefs);

        logger.fine("Initializing with Mark: " + mark + " and user prefs " + userPrefs);

        versionedMark = new VersionedMark(mark);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBookmarks = new FilteredList<>(versionedMark.getBookmarkList());
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
        versionedMark.resetData(mark);
    }

    @Override
    public ReadOnlyMark getMark() {
        return versionedMark;
    }

    @Override
    public boolean hasBookmark(Bookmark bookmark) {
        requireNonNull(bookmark);
        return versionedMark.hasBookmark(bookmark);
    }

    @Override
    public void deleteBookmark(Bookmark target) {
        versionedMark.removeBookmark(target);
    }

    @Override
    public void addBookmark(Bookmark bookmark) {
        versionedMark.addBookmark(bookmark);
        updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);
    }

    @Override
    public void setBookmark(Bookmark target, Bookmark editedBookmark) {
        requireAllNonNull(target, editedBookmark);

        versionedMark.setBookmark(target, editedBookmark);
    }

    @Override
    public void addFolder(Folder folder, Folder parentFolder) {
        requireAllNonNull(folder, parentFolder);
        versionedMark.addFolder(folder, parentFolder);
    }

    @Override
    public boolean hasFolder(Folder folder) {
        return versionedMark.hasFolder(folder);
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

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoMark() {
        return versionedMark.canUndo();
    }

    @Override
    public boolean canRedoMark() {
        return versionedMark.canRedo();
    }

    @Override
    public void undoMark() {
        versionedMark.undo();
    }

    @Override
    public void redoMark() {
        versionedMark.redo();
    }

    @Override
    public void saveMark() {
        versionedMark.save();
    }

    //=========== Current bookmark ===========================================================================

    @Override
    public SimpleObjectProperty<Url> getCurrentUrlProperty() {
        return currentUrl;
    }

    @Override
    public Url getCurrentUrl() {
        return currentUrl.getValue();
    }

    @Override
    public void setCurrentUrl(Url url) {
        currentUrl.setValue(url);
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
        return versionedMark.equals(other.versionedMark)
                && userPrefs.equals(other.userPrefs)
                && filteredBookmarks.equals(other.filteredBookmarks)
                && (currentUrl.getValue() == null
                    ? other.currentUrl.getValue() == null
                    : currentUrl.getValue().equals(other.currentUrl.getValue()));
    }

    //=========== Reminder =================================================================================

    /**
     * Adds a reminder that opens a specific bookmark.
     *
     * @param bookmark the bookmark to be opened.
     * @param reminder the reminder that is added.
     */
    public void addReminder(Bookmark bookmark, Reminder reminder) {
        versionedMark.addReminder(bookmark, reminder);
    }

    /**
     * Removes a specific reminder.
     *
     * @param reminder the reminder to be removed.
     */
    public void removeReminder(Reminder reminder) {
        versionedMark.removeReminder(reminder);
    }

    /**
     * Edits a specific reminder.
     *
     * @param targetReminder the reminder to be edited.
     * @param editedReminder the edited reminder.
     */
    public void editReminder(Reminder targetReminder, Reminder editedReminder) {
        versionedMark.editReminder(targetReminder, editedReminder);
    }

    /**
     * Gets all reminders in ascending time order.
     *
     * @return a list of all reminders in ascending time order.
     */
    public ObservableList<Reminder> getReminders() {
        return versionedMark.getReminders();
    }
}
