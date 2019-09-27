package seedu.mark.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.UniqueBookmarkList;

/**
 * Wraps all data at the bookmark-manager level
 * Duplicates are not allowed (by .isSameBookmark comparison)
 */
public class BookmarkManager implements ReadOnlyBookmarkManager {

    private final UniqueBookmarkList bookmarks;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        bookmarks = new UniqueBookmarkList();
    }

    public BookmarkManager() {}

    /**
     * Creates a BookmarkManager using the Bookmarks in the {@code toBeCopied}
     */
    public BookmarkManager(ReadOnlyBookmarkManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the bookmark list with {@code bookmarks}.
     * {@code bookmarks} must not contain duplicate bookmarks.
     */
    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks.setBookmarks(bookmarks);
    }

    /**
     * Resets the existing data of this {@code BookmarkManager} with {@code newData}.
     */
    public void resetData(ReadOnlyBookmarkManager newData) {
        requireNonNull(newData);

        setBookmarks(newData.getBookmarkList());
    }

    //// bookmark-level operations

    /**
     * Returns true if a bookmark with the same identity as {@code bookmark} exists in the bookmark manager.
     */
    public boolean hasBookmark(Bookmark bookmark) {
        requireNonNull(bookmark);
        return bookmarks.contains(bookmark);
    }

    /**
     * Adds a bookmark to the bookmark manager.
     * The bookmark must not already exist in the bookmark manager.
     */
    public void addBookmark(Bookmark p) {
        bookmarks.add(p);
    }

    /**
     * Replaces the given bookmark {@code target} in the list with {@code editedBookmark}.
     * {@code target} must exist in the bookmark manager.
     * The bookmark identity of {@code editedBookmark} must not be the same as another existing bookmark in the
     * bookmark manager.
     */
    public void setBookmark(Bookmark target, Bookmark editedBookmark) {
        requireNonNull(editedBookmark);

        bookmarks.setBookmark(target, editedBookmark);
    }

    /**
     * Removes {@code key} from this {@code BookmarkManager}.
     * {@code key} must exist in the bookmark manager.
     */
    public void removeBookmark(Bookmark key) {
        bookmarks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return bookmarks.asUnmodifiableObservableList().size() + " bookmarks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Bookmark> getBookmarkList() {
        return bookmarks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookmarkManager // instanceof handles nulls
                && bookmarks.equals(((BookmarkManager) other).bookmarks));
    }

    @Override
    public int hashCode() {
        return bookmarks.hashCode();
    }
}
