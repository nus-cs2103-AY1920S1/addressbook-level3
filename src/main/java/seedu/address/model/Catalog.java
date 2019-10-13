package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.UniqueBookList;
import seedu.address.model.borrower.BorrowerId;

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
     * Returns true if a book with the same serial number as {@code book} exists in the catalog.
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

    public boolean checkIfSerialNumberExists(SerialNumber sn) {
        return books.containsSerialNumber(sn);
    }

    /**
     * Removes {@code key} from this {@code Catalog}.
     * {@code key} must exist in the catalog.
     */
    public void removeBook(Book key) {
        books.remove(key);
    }

    /**
     * Returns a list of loaned books from the current catalog.
     *
     * @return an ObservableList of books that are loaned out.
     */
    public ObservableList<Book> getLoanedBooks() {
        List<Book> loanedBooks = getBookList()
                .stream()
                .filter(book -> book.isCurrentlyLoanedOut())
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(loanedBooks);
    }

    /**
     * Returns a list of overdue books from the current catalog.
     *
     * @return an ObservableList of books that are overdue.
     */
    public ObservableList<Book> getOverdueBooks() {
        List<Book> loanedBooks = getBookList()
                .stream()
                .filter(book -> book.isOverdue())
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(loanedBooks);
    }

    public ObservableList<BorrowerId> getOverdueBooksBorrowersId() {
        List<BorrowerId> borrowersId = getOverdueBooks()
                .stream()
                .filter(book -> book.isCurrentlyLoanedOut()) // safety check to ensure loan is present
                .map(book -> book.getLoan().get()) // get Loan object from each book
                .map(loan -> loan.getBorrowerId()) // get BorrowerId from each Loan object
                .collect(Collectors.toList()); // collect it to a list
        return FXCollections.observableArrayList(borrowersId);
    }

    /**
     * Returns a list of available books from the current catalog.
     *
     * @return an ObservableList of books that are available.
     */
    public ObservableList<Book> getAvailableBooks() {
        List<Book> availableBooks = getBookList()
                .stream()
                .filter(book -> !book.isCurrentlyLoanedOut())
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(availableBooks);
    }

    //// util methods

    public Book getBook(SerialNumber bookSn) {
        return books.getBook(bookSn);
    }

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
