package seedu.mark.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.UniqueBookmarkList;
import seedu.mark.model.folderstructure.FolderStructure;

/**
 * Wraps all data at the bookmark-manager level
 * Duplicates are not allowed (by .isSameBookmark comparison)
 */
public class Mark implements ReadOnlyMark {

    private final UniqueBookmarkList bookmarks;

    private FolderStructure folderStructure;

    public Mark() {
        bookmarks = new UniqueBookmarkList();
        folderStructure = new FolderStructure(Folder.DEFAULT_FOLDER_NAME, new ArrayList<>());
    }

    /**
     * Creates an instance of Mark using the Bookmarks in the {@code toBeCopied}
     */
    public Mark(ReadOnlyMark toBeCopied) {
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
     * Resets the existing data of this {@code Mark} with {@code newData}.
     */
    public void resetData(ReadOnlyMark newData) {
        requireNonNull(newData);

        setBookmarks(newData.getBookmarkList());
        setFolderStructure(newData.getFolderStructure().clone());
    }

    //// bookmark-level operations

    /**
     * Returns true if a bookmark with the same identity as {@code bookmark} exists in Mark.
     */
    public boolean hasBookmark(Bookmark bookmark) {
        requireNonNull(bookmark);
        return bookmarks.contains(bookmark);
    }

    /**
     * Adds a bookmark to Mark.
     * The bookmark must not already exist in Mark.
     */
    public void addBookmark(Bookmark p) {
        bookmarks.add(p);
    }

    /**
     * Replaces the given bookmark {@code target} in the list with {@code editedBookmark}.
     * {@code target} must exist in Mark.
     * The bookmark identity of {@code editedBookmark} must not be the same as another
     * existing bookmark in Mark.
     */
    public void setBookmark(Bookmark target, Bookmark editedBookmark) {
        requireNonNull(editedBookmark);

        bookmarks.setBookmark(target, editedBookmark);
    }

    /**
     * Removes {@code key} from this {@code Mark} instance.
     * {@code key} must exist in Mark.
     */
    public void removeBookmark(Bookmark key) {
        bookmarks.remove(key);
    }

    //// folder operations

    /**
     * Replaces the folder structure with {@code bookmarks}.
     * {@code bookmarks} must not contain duplicate bookmarks.
     */
    public void setFolderStructure(FolderStructure folderStructure) {
        this.folderStructure.getSubfolders().clear();
        this.folderStructure.getSubfolders().addAll(folderStructure.getSubfolders());
    }


    /**
     * Creates a new folder with name {@code folderName} under {@code parentFolderName}.
     * {@code folderName} must not exist.
     * {@code parentFolderName} must exist.
     */
    public void addFolder(String folderName, String parentFolderName) {
        this.folderStructure.addFolder(folderName, parentFolderName);
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
    public FolderStructure getFolderStructure() {
        return folderStructure;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mark // instanceof handles nulls
                && bookmarks.equals(((Mark) other).bookmarks)
                && folderStructure.equals(((Mark) other).folderStructure));
    }

    @Override
    public int hashCode() {
        return bookmarks.hashCode();
    }
}
