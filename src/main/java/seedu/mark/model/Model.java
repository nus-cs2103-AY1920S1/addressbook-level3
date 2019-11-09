package seedu.mark.model;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Reminder;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluates to true
     */
    Predicate<Bookmark> PREDICATE_SHOW_ALL_BOOKMARKS = unused -> true;

    /** {@code Predicate} that always evaluate to false */
    Predicate<Bookmark> PREDICATE_SHOW_NO_BOOKMARKS = unused -> false;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Mark file path.
     */
    Path getMarkFilePath();

    /**
     * Sets the user prefs' Mark file path.
     */
    void setMarkFilePath(Path markFilePath);

    /**
     * Replaces Mark data with the data in {@code mark}.
     */
    void setMark(ReadOnlyMark mark);

    /**
     * Returns the Mark
     */
    ReadOnlyMark getMark();

    /**
     * Returns true if a bookmark with the same identity as {@code bookmark} exists in Mark.
     */
    boolean hasBookmark(Bookmark bookmark);

    /**
     * Deletes the given bookmark.
     * The bookmark must exist in Mark.
     */
    void deleteBookmark(Bookmark target);

    /**
     * Tags the given bookmark as a favorite bookmark.
     * The bookmark must exist in Mark.
     */
    void favoriteBookmark(Bookmark target);

    /**
     * Adds the given bookmark.
     * {@code bookmark} must not already exist in Mark.
     */
    void addBookmark(Bookmark bookmark);

    /**
     * Replaces the given bookmark {@code target} with {@code editedBookmark}.
     * {@code target} must exist in Mark.
     * The bookmark identity of {@code editedBookmark} must not be the same as another existing bookmark in Mark.
     */
    void setBookmark(Bookmark target, Bookmark editedBookmark);

    /**
     * Attempts to add bookmarks from the given list to Mark. Bookmarks that
     * already exist are ignored.
     */
    void addBookmarks(List<Bookmark> bookmarksToAdd);

    /**
     * Returns an unmodifiable view of the filtered bookmark list
     */
    ObservableList<Bookmark> getFilteredBookmarkList();

    /** Returns an unmodifiable view of the favorite bookmark list */
    ObservableList<Bookmark> getFavoriteBookmarkList();

    /**
     * Updates the filter of the filtered bookmark list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookmarkList(Predicate<Bookmark> predicate);

    void addFolder(Folder folder, Folder parentFolder);

    boolean hasFolder(Folder folder);

    /**
     * Checks whether Mark contains this {@code tagger}.
     */
    boolean hasTagger(SelectiveBookmarkTagger tagger);

    /**
     * Adds a {@code tagger} to Mark.
     */
    void addTagger(SelectiveBookmarkTagger tagger);

    /**
     * Removes the tagger with the given {@code taggerName} from Mark.
     *
     * @param taggerName name of the tagger to be removed.
     * @return false if the tagger is not found.
     */
    boolean removeTagger(String taggerName);

    /**
     * Activates all taggers in Mark to apply tags to Mark's bookmarks based
     * on their respective conditions.
     */
    void applyAllTaggers();

    /*
     * Wrapper for current url.
     * null if not present.
     */
    SimpleObjectProperty<Url> getCurrentUrlProperty();

    /**
     * Returns the current url.
     * null if not present.
     */
    Url getCurrentUrl();

    /**
     * Sets the current url.
     */
    void setCurrentUrl(Url url);

    /**
     * Returns true if the model has previous Mark states to restore.
     *
     * @param steps The number of previous Mark states to check
     */
    boolean canUndoMark(int steps);

    int getMaxStepsToUndo();

    /**
     * Returns true if the model has undone Mark states to restore.
     *
     * @param steps The number of undone Mark states to check
     */
    boolean canRedoMark(int steps);

    int getMaxStepsToRedo();

    /**
     * Restores the model's Mark to its previous state.
     *
     * @param steps The number of steps to undo
     * @return The record
     */
    String undoMark(int steps);

    /**
     * Restores the model's Mark to its previously undone state.
     *
     * @param steps The number of steps to redo
     * @return The record
     */
    String redoMark(int steps);

    /**
     * Saves the current Mark state for undo/redo.
     *
     * @param record The record for the state
     */
    void saveMark(String record);

    /**
     * Adds a reminder that opens a specific bookmark.
     */
    void addReminder(Bookmark bookmark, Reminder reminder);

    /**
     * Removes a specific reminder.
     */
    void removeReminder(Reminder reminder);

    /**
     * Edits a specific reminder.
     */
    void editReminder(Reminder targetReminder, Reminder editedReminder);

    /**
     * Finds the bookmark for a specific reminder.
     *
     * @param reminder the reminder of the bookmark.
     * @return the bookmark of the reminder.
     */
    public Bookmark getBookmarkFromReminder(Reminder reminder);

    /**
     * Checks if the bookmark already has reminder.
     *
     * @param bookmark the bookmark to check.
     * @return whether the bookmark already has a reminder.
     */
    boolean isBookmarkHasReminder(Bookmark bookmark);

    /**
     * Gets all reminders in ascending time order.
     */
    ObservableList<Reminder> getReminders();

    /**
     * Sets the reminders in Mark.
     */
    void setReminders();

    /**
     * Returns a view of the annotated document.
     */
    ObservableList<Paragraph> getObservableDocument();

    /**
     * Updates the view of document to the document given.
     *
     * @param doc Document to update view and be shown.
     */
    void updateDocument(OfflineDocument doc);

    /**
     * Updates the specified bookmark to be the one to display its cache
     */
    void updateCurrentDisplayedCache(Bookmark bookmarkToDisplayCache);

    /**
     * Returns the observable of the bookmark with its cache currently displayed.
     *
     * @return the observable bookmark property
     */
    SimpleObjectProperty<Bookmark> getBookmarkDisplayingCacheProperty();


    /**
     * Starts mark's timer.
     */
    void startTimer(ScheduledExecutorService executor);

    ObservableValue<String> getObservableOfflineDocNameCurrentlyShowing();

    void setOfflineDocNameCurrentlyShowing(String name);

}
