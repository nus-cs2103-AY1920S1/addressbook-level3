package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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

    // ================================================================ Catalog

    public Path getCatalogFilePath();

    /**
     * Returns the user prefs' catalog file path.
     */
    public void setCatalogFilePath(Path catalogFilePath);

    /** Returns the Catalog */
    ReadOnlyCatalog getCatalog();

    /**
     * Returns the user prefs' loan records file path.
     */
    void setCatalog(ReadOnlyCatalog addressBook);

    /**
     * Returns true if a book with the same identity as {@code book} exists in the catalog.
     */
    boolean hasBook(Book book);
    /**
     * Returns the user prefs' catalog file path.
     */
    void deleteBook(Book target);

    void addBook(Book book);

    void setBook(Book target, Book editedBook);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Book> getFilteredBookList();

    void updateFilteredBookList(Predicate<Book> predicate);

    Model excludeBookBeingReplaced(Book toBeReplaced);

    // ================================================================ LoanRecords

    Path getLoanRecordsFilePath();

    void setLoanRecordsFilePath(Path loanRecordsFilePath);

    /** Returns the LoanRecords*/
    ReadOnlyLoanRecords getLoanRecords();

    // ================================================================ BorrowerRecords

    /**
     * Returns the user prefs' borrower records file path.
     */
    Path getBorrowerRecordsFilePath();

    /**
     * Sets the user prefs' address borrower records path.
     */
    void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath);

    /** Returns the BorrowerRecords*/
    ReadOnlyBorrowerRecords getBorrowerRecords();

    /**
     * If in serve mode, returns borrower that is being served.
     * Else, returns empty optional.
     *
     * @return Borrower that is being Served.
     */
    Optional<Borrower> getServingBorrower();

    /**
     * Returns true if Liberry is currently in Serve mode, false otherwise.
     *
     * @return True if Liberry is currently in Serve mode.
     */
    boolean isServeMode();

    boolean hasBorrower(Borrower borrower);
    /**
     * Returns the user prefs' catalog file path.
     */
    void deleteBorrower(Borrower target);

    void registerBorrower(Borrower borrower);
}
