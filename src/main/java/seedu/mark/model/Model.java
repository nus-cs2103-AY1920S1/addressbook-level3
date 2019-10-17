package seedu.mark.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.model.reminder.Reminder;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Bookmark> PREDICATE_SHOW_ALL_BOOKMARKS = unused -> true;

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

    /** Returns the Mark */
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

    /** Returns an unmodifiable view of the filtered bookmark list */
    ObservableList<Bookmark> getFilteredBookmarkList();

    /**
     * Updates the filter of the filtered bookmark list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookmarkList(Predicate<Bookmark> predicate);

    void addFolder(Folder folder, Folder parentFolder);

    boolean hasFolder(Folder folder);

    /**
     * Attempts to add a structure of folders to Mark.
     * Implementation to be decided.
     */
    void addFolders(FolderStructure foldersToAdd);

    /**
     * Adds a {@code tagger} to Mark.
     */
    void addTagger(SelectiveBookmarkTagger tagger);

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
     */
    boolean canUndoMark();

    /**
     * Returns true if the model has undone Mark states to restore.
     */
    boolean canRedoMark();

    /**
     * Restores the model's Mark to its previous state.
     */
    void undoMark();

    /**
     * Restores the model's Mark to its previously undone state.
     */
    void redoMark();

    /**
     * Saves the current Mark state for undo/redo.
     */
    void saveMark();

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
     * Returns a view of the annotated document.
     */
    ObservableList<Paragraph> getObservableDocument();

    /**
     * Updates the view of document to the document given.
     * @param doc Document to update view and be shown.
     */
    void updateDocument(OfflineDocument doc);

}
