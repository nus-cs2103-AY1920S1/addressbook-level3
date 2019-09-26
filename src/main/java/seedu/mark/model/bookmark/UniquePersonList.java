package seedu.mark.model.bookmark;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.exceptions.DuplicateBookmarkException;
import seedu.mark.model.bookmark.exceptions.BookmarkNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A bookmark is considered unique by comparing using {@code Bookmark#isSamePerson(Bookmark)}. As such, adding and updating of
 * persons uses Bookmark#isSamePerson(Bookmark) for equality so as to ensure that the bookmark being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a bookmark uses Bookmark#equals(Object) so
 * as to ensure that the bookmark with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Bookmark#isSamePerson(Bookmark)
 */
public class UniquePersonList implements Iterable<Bookmark> {

    private final ObservableList<Bookmark> internalList = FXCollections.observableArrayList();
    private final ObservableList<Bookmark> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent bookmark as the given argument.
     */
    public boolean contains(Bookmark toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a bookmark to the list.
     * The bookmark must not already exist in the list.
     */
    public void add(Bookmark toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookmarkException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the bookmark {@code target} in the list with {@code editedBookmark}.
     * {@code target} must exist in the list.
     * The bookmark identity of {@code editedBookmark} must not be the same as another existing bookmark in the list.
     */
    public void setPerson(Bookmark target, Bookmark editedBookmark) {
        requireAllNonNull(target, editedBookmark);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookmarkNotFoundException();
        }

        if (!target.isSamePerson(editedBookmark) && contains(editedBookmark)) {
            throw new DuplicateBookmarkException();
        }

        internalList.set(index, editedBookmark);
    }

    /**
     * Removes the equivalent bookmark from the list.
     * The bookmark must exist in the list.
     */
    public void remove(Bookmark toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BookmarkNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code bookmarks}.
     * {@code bookmarks} must not contain duplicate bookmarks.
     */
    public void setPersons(List<Bookmark> bookmarks) {
        requireAllNonNull(bookmarks);
        if (!personsAreUnique(bookmarks)) {
            throw new DuplicateBookmarkException();
        }

        internalList.setAll(bookmarks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Bookmark> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Bookmark> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code bookmarks} contains only unique bookmarks.
     */
    private boolean personsAreUnique(List<Bookmark> bookmarks) {
        for (int i = 0; i < bookmarks.size() - 1; i++) {
            for (int j = i + 1; j < bookmarks.size(); j++) {
                if (bookmarks.get(i).isSamePerson(bookmarks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
