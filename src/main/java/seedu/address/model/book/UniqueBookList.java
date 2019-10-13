package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * A list of books that enforces uniqueness between its elements and does not allow nulls.
 * A book is considered unique by comparing using {@code Book#equals(Book)}. As such, adding and updating of
 * books uses Book#equals(Book) for equality so as to ensure that the book being added or updated is
 * unique in terms of identity in the UniqueBookList. However, the removal of a book uses Book#equals(Object) so
 * as to ensure that the book with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueBookList implements Iterable<Book> {

    private final HashMap<SerialNumber, Book> booksMap = new HashMap<>();
    private final ObservableList<Book> internalList = FXCollections.observableArrayList();
    private final ObservableList<Book> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent book as the given argument.
     */
    public boolean contains(Book toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains a book with the same serial number as the given argument.
     */
    public boolean containsSerialNumber(SerialNumber toCheck) {
        requireNonNull(toCheck);
        return booksMap.containsKey(toCheck);
    }

    /**
     * Adds a book to the list.
     * The book must not already exist in the list.
     */
    public void add(Book toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookException();
        }
        internalList.add(toAdd);
        booksMap.put(toAdd.getSerialNumber(), toAdd);
    }

    /**
     * Replaces the book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the list.
     * The book identity of {@code editedBook} must not be the same as another existing book in the list.
     */
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookNotFoundException();
        }
        if (!target.equals(editedBook) && contains(editedBook)) {
            throw new DuplicateBookException();
        }
        internalList.set(index, editedBook);

        booksMap.remove(target.getSerialNumber());
        booksMap.put(editedBook.getSerialNumber(), editedBook);
    }

    /**
     * Removes the equivalent book from the list.
     * The book must exist in the list.
     */
    public void remove(Book toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BookNotFoundException();
        }
        booksMap.remove(toRemove.getSerialNumber());
    }

    public void setBooks(UniqueBookList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        booksMap.clear();
        replacement.forEach(book -> booksMap.put(book.getSerialNumber(), book));
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setBooks(List<Book> books) {
        requireAllNonNull(books);
        if (!booksAreUnique(books)) {
            throw new DuplicateBookException();
        }

        internalList.setAll(books);
        booksMap.clear();
        books.forEach(book -> booksMap.put(book.getSerialNumber(), book));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Book> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Book> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBookList // instanceof handles nulls
                        && internalList.equals(((UniqueBookList) other).internalList)
                        && booksMap.equals(((UniqueBookList) other).booksMap));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code books} contains only unique books.
     */
    private boolean booksAreUnique(List<Book> books) {
        for (int i = 0; i < books.size() - 1; i++) {
            for (int j = i + 1; j < books.size(); j++) {
                if (books.get(i).equals(books.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
