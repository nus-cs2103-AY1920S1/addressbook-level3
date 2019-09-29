package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.UniqueBookList;

/**
 * Wraps all data at the catalog level
 * Duplicates are not allowed (by .isSameBook comparison)
 */
public class Catalog implements ReadOnlyCatalog {

    private final UniqueBookList books;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        books = new UniqueBookList();
    }

    public Catalog() {}

    /**
     * Creates a Catalog using the Books in the {@code toBeCopied}
     */
    public Catalog(ReadOnlyCatalog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the book list with {@code books}.
     * {@code books} must not contain duplicate books.
     */
    public void setBooks(List<Book> books) {
        this.books.setBooks(books);
    }

    /**
     * Resets the existing data of this {@code Catalog} with {@code newData}.
     */
    public void resetData(ReadOnlyCatalog newData) {
        requireNonNull(newData);

        setBooks(newData.getBookList());
    }

    //// person-level operations

    /**
<<<<<<< HEAD
     * Returns true if a book with the same identity as {@code book} exists in the address book.
=======
     * Returns true if a book with the same serial number as {@code book} exists in the catalog.
>>>>>>> 76edc3518025011d8a382c0c40e511828d7408d5
     */
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return checkIfSerialNumberExists(book.getSerialNumber());
    }

    /**
     * Adds a book to the catalog.
     * The book must not already exist in the catalog.
     */
    public void addBook(Book p) {
        books.add(p);
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the catalog.
     * The book identity of {@code editedBook} must not be the same as another existing book in the catalog.
     */
    public void setBook(Book target, Book editedBook) {
        requireNonNull(editedBook);

        books.setBook(target, editedBook);
    }

    boolean checkIfSerialNumberExists(SerialNumber sn) {
        return books.containsSerialNumber(sn);
    }

    /**
<<<<<<< HEAD
     * Removes {@code key} from this {@code catalog}.
=======
     * Removes {@code key} from this {@code Catalog}.
>>>>>>> 76edc3518025011d8a382c0c40e511828d7408d5
     * {@code key} must exist in the catalog.
     */
    public void removeBook(Book key) {
        books.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return books.asUnmodifiableObservableList().size() + " books";
        // TODO: refine later
    }

    @Override
    public ObservableList<Book> getBookList() {
        return books.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Catalog // instanceof handles nulls
                && books.equals(((Catalog) other).books));
    }

    @Override
    public int hashCode() {
        return books.hashCode();
    }
}
