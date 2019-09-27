package seedu.mark.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.model.bookmark.Bookmark;

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
     * Returns the user prefs' bookmark manager file path.
     */
    Path getBookmarkManagerFilePath();

    /**
     * Sets the user prefs' bookmark manager file path.
     */
    void setBookmarkManagerFilePath(Path bookmarkManagerFilePath);

    /**
     * Replaces bookmark manager data with the data in {@code bookmarkManager}.
     */
    void setBookmarkManager(ReadOnlyBookmarkManager bookmarkManager);

    /** Returns the BookmarkManager */
    ReadOnlyBookmarkManager getBookmarkManager();

    /**
     * Returns true if a bookmark with the same identity as {@code bookmark} exists in the bookmark manager.
     */
    boolean hasBookmark(Bookmark bookmark);

    /**
     * Deletes the given bookmark.
     * The bookmark must exist in the bookmark manager.
     */
    void deleteBookmark(Bookmark target);

    /**
     * Adds the given bookmark.
     * {@code bookmark} must not already exist in the bookmark manager.
     */
    void addBookmark(Bookmark bookmark);

    /**
     * Replaces the given bookmark {@code target} with {@code editedBookmark}.
     * {@code target} must exist in the bookmark manager.
     * The bookmark identity of {@code editedBookmark} must not be the same as another existing bookmark in the bookmark manager.
     */
    void setBookmark(Bookmark target, Bookmark editedBookmark);

    /** Returns an unmodifiable view of the filtered bookmark list */
    ObservableList<Bookmark> getFilteredBookmarkList();

    /**
     * Updates the filter of the filtered bookmark list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookmarkList(Predicate<Bookmark> predicate);
}
