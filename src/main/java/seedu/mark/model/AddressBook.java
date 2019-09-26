package seedu.mark.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.UniqueBookmarkList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBookmark comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueBookmarkList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueBookmarkList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the bookmark list with {@code bookmarks}.
     * {@code bookmarks} must not contain duplicate bookmarks.
     */
    public void setPersons(List<Bookmark> bookmarks) {
        this.persons.setBookmarks(bookmarks);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// bookmark-level operations

    /**
     * Returns true if a bookmark with the same identity as {@code bookmark} exists in the address book.
     */
    public boolean hasPerson(Bookmark bookmark) {
        requireNonNull(bookmark);
        return persons.contains(bookmark);
    }

    /**
     * Adds a bookmark to the address book.
     * The bookmark must not already exist in the address book.
     */
    public void addPerson(Bookmark p) {
        persons.add(p);
    }

    /**
     * Replaces the given bookmark {@code target} in the list with {@code editedBookmark}.
     * {@code target} must exist in the address book.
     * The bookmark identity of {@code editedBookmark} must not be the same as another existing bookmark in the address book.
     */
    public void setPerson(Bookmark target, Bookmark editedBookmark) {
        requireNonNull(editedBookmark);

        persons.setBookmark(target, editedBookmark);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Bookmark key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Bookmark> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
