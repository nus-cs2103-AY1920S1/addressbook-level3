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

    private final Catalog catalog;
    private final UserPrefs userPrefs;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given catalog and userPrefs.
     */
    public ModelManager(ReadOnlyCatalog catalog, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(catalog, userPrefs);

        logger.fine("Initializing with catalog: " + catalog + " and user prefs " + userPrefs);

        this.catalog = new Catalog(catalog);
        this.userPrefs = new UserPrefs(userPrefs);
        SerialNumberGenerator.setCatalog((Catalog) catalog);
        filteredBooks = new FilteredList<>(this.catalog.getBookList());
    }

    public ModelManager() {
        this(new Catalog(), new UserPrefs());
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

    //=========== Filtered Person List Accessors =============================================================

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
        return catalog.equals(other.catalog)
                && userPrefs.equals(other.userPrefs)
                && filteredBooks.equals(other.filteredBooks);
    }

}
