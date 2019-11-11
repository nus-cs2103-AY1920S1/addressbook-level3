package seedu.mark.model;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.model.tag.Tag;

/**
 * Represents the in-memory model of the Mark data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedMark versionedMark;
    private final UserPrefs userPrefs;
    private final FilteredList<Bookmark> filteredBookmarks;
    private final FilteredList<Bookmark> favoriteBookmarks;
    private final SimpleObjectProperty<Url> currentUrl = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Bookmark> bookmarkToDisplayCache = new SimpleObjectProperty<>();


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
        favoriteBookmarks = new FilteredList<>(versionedMark.getBookmarkList(),
            bookmark -> bookmark.containsTag(Tag.FAVORITE));
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
    public void favoriteBookmark(Bookmark target) {
        versionedMark.favoriteBookmark(target);
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
    public void addBookmarks(List<Bookmark> bookmarksToAdd) {
        requireNonNull(bookmarksToAdd);

        for (Bookmark bookmark : bookmarksToAdd) {
            if (hasBookmark(bookmark)) {
                logger.fine("Duplicate bookmark was not added: " + bookmark);
                continue;
            }
            logger.fine("Bookmark added: " + bookmark);
            addBookmark(bookmark);
        }
    }

    @Override
    public void addFolder(Folder folder, Folder parentFolder) {
        requireAllNonNull(folder, parentFolder);
        versionedMark.addFolder(folder, parentFolder);
    }

    @Override
    public void renameFolder(Folder from, Folder to) {
        requireAllNonNull(from, to);
        versionedMark.renameFolder(from, to);
    }

    @Override
    public void deleteFolder(Folder folder) {
        requireNonNull(folder);
        versionedMark.deleteFolder(folder);
    }

    @Override
    public boolean canDeleteFolder(Folder folder) {
        requireNonNull(folder);
        return getMark().getBookmarkList().stream().noneMatch(bookmark -> bookmark.getFolder().equals(folder))
                && getMark().getFolderStructure().find(folder).getSubfolders().isEmpty();
    }

    @Override
    public boolean hasFolder(Folder folder) {
        requireNonNull(folder);

        return versionedMark.hasFolder(folder);
    }

    @Override
    public boolean hasTagger(SelectiveBookmarkTagger tagger) {
        requireNonNull(tagger);
        return versionedMark.hasTagger(tagger);
    }

    @Override
    public void addTagger(SelectiveBookmarkTagger tagger) {
        requireNonNull(tagger);
        versionedMark.addTagger(tagger);
    }

    @Override
    public Optional<SelectiveBookmarkTagger> removeTagger(String taggerName) {
        requireNonNull(taggerName);
        return versionedMark.removeTagger(taggerName);
    }

    @Override
    public void applyAllTaggers() {
        versionedMark.applyAllTaggers();
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
    public ObservableList<Bookmark> getFavoriteBookmarkList() {
        return favoriteBookmarks;
    }

    @Override
    public void updateFilteredBookmarkList(Predicate<Bookmark> predicate) {
        requireNonNull(predicate);
        filteredBookmarks.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoMark(int steps) {
        return versionedMark.canUndo(steps);
    }

    @Override
    public int getMaxStepsToUndo() {
        return versionedMark.getMaxStepsToUndo();
    }

    @Override
    public boolean canRedoMark(int steps) {
        return versionedMark.canRedo(steps);
    }

    @Override
    public int getMaxStepsToRedo() {
        return versionedMark.getMaxStepsToRedo();
    }

    @Override
    public String undoMark(int steps) {
        return versionedMark.undo(steps);
    }

    @Override
    public String redoMark(int steps) {
        return versionedMark.redo(steps);
    }

    @Override
    public void saveMark(String record) {
        requireNonNull(record);

        versionedMark.save(record);
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

    //=========== Current offline ============================================================================

    @Override
    public ObservableList<Paragraph> getObservableDocument() {
        return versionedMark.getAnnotatedDocument();
    }

    @Override
    public void updateDocument(OfflineDocument doc) {
        versionedMark.setAnnotatedDocument(FXCollections.observableArrayList(doc.getCollection()));
    }

    @Override
    public ObservableValue<String> getObservableOfflineDocNameCurrentlyShowing() {
        return versionedMark.getOfflineDocCurrentlyShowing();
    }

    @Override
    public void setOfflineDocNameCurrentlyShowing(String name) {
        versionedMark.setOfflineDocCurrentlyShowing(name);
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
     * Checks if the bookmark already has reminder.
     *
     * @param bookmark the bookmark to check.
     * @return whether the bookmark already has a reminder.
     */
    public boolean isBookmarkHasReminder(Bookmark bookmark) {
        return versionedMark.isBookmarkHasReminder(bookmark);
    }

    /**
     * Gets all reminders in ascending time order.
     *
     * @return a list of all reminders in ascending time order.
     */
    public ObservableList<Reminder> getReminders() {
        return versionedMark.getReminders();
    }


    /**
     * Sets the reminders in Mark.
     */
    public void setReminders() {
        versionedMark.setReminders();
    }

    /**
     * Finds the bookmark for a specific reminder.
     *
     * @param reminder the reminder of the bookmark.
     * @return the bookmark of the reminder.
     */
    public Bookmark getBookmarkFromReminder(Reminder reminder) {
        return versionedMark.getBookmarkFromReminder(reminder);
    }

    //=========== Cache =================================================================================
    @Override
    public void updateCurrentDisplayedCache(Bookmark bookmarkToDisplayCache) {
        this.bookmarkToDisplayCache.set(bookmarkToDisplayCache);
    }

    @Override
    public SimpleObjectProperty<Bookmark> getBookmarkDisplayingCacheProperty() {
        return bookmarkToDisplayCache;
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

    @Override
    /**
     * Starts mark's timer.
     */
    public void startTimer(ScheduledExecutorService executor) {
        versionedMark.deleteExpiredReminder(executor);
    }

}
