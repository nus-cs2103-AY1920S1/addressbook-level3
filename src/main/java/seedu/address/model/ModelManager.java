package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.book.Book;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final LoanRecords loanRecords;
    private final Catalog catalog;
    private final BorrowerRecords borrowerRecords;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     * TODO change
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs,
                        ReadOnlyLoanRecords loanRecords, ReadOnlyCatalog catalog,
                        ReadOnlyBorrowerRecords borrowerRecords) 
        super();
//        requireAllNonNull(addressBook, userPrefs, catalog);

        logger.fine("Initializing with catalog: " + catalog + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        // testing loan records
        this.loanRecords = new LoanRecords(loanRecords);
        this.loanRecords.populateLoans();
        // testing
        this.catalog = new Catalog(catalog);
        this.catalog.populateBooks();
        // testing
        this.borrowerRecords = new BorrowerRecords(borrowerRecords);
        this.borrowerRecords.populateBorrowers();
        SerialNumberGenerator.setCatalog((Catalog) catalog);
    }

    public ModelManager() {
        this(new UserPrefs(),
                new LoanRecords(), new Catalog(), new BorrowerRecords());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCatalogFilePath() {
        return userPrefs.getCatalogFilePath();
    }

    @Override
    public void setCatalogFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setCatalogFilePath(addressBookFilePath);
    }


    //=========== Catalog ================================================================================

    @Override
    public void setCatalog(ReadOnlyCatalog addressBook) {
        this.catalog.resetData(addressBook);
    }

    @Override
    public ReadOnlyCatalog getCatalog() {
        return catalog;
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return catalog.hasBook(book);
    }

    @Override
    public void deleteBook(Book target) {
        catalog.removeBook(target);
    }

    @Override
    public void addBook(Book book) {
        catalog.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        catalog.setBook(target, editedBook);
    }
    
    public Path getLoanRecordsFilePath() {
        return userPrefs.getLoanRecordsFilePath();
    }

    @Override
    public void setLoanRecordsFilePath(Path loanRecordsFilePath) {
        requireNonNull(loanRecordsFilePath);
        userPrefs.setLoanRecordsFilePath(loanRecordsFilePath);
    }

    @Override
    public Path getCatalogFilePath() {
        return userPrefs.getCatalogFilePath();
    }

    @Override
    public void setCatalogFilePath(Path catalogFilePath) {
        requireNonNull(catalogFilePath);
        userPrefs.setCatalogFilePath(catalogFilePath);
    }

    @Override
    public Path getBorrowerRecordsFilePath() {
        return userPrefs.getBorrowerRecordsFilePath();
    }

    @Override
    public void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath) {
        requireNonNull(borrowerRecordsFilePath);
        userPrefs.setBorrowerRecordsFilePath(borrowerRecordsFilePath);
    }

    //=========== Loan Records ===============================================================================

    public ReadOnlyLoanRecords getLoanRecords() {
        return loanRecords;
    }

    //=========== Catalog ===============================================================================


    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return filteredBooks;
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }

    //=========== BorrowerRecords ===============================================================================

    public ReadOnlyBorrowerRecords getBorrowerRecords() {
        return borrowerRecords;
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && loanRecords.equals(other.loanRecords)
                && catalog.equals(other.catalog)
                && borrowerRecords.equals(other.borrowerRecords);
    }

}
