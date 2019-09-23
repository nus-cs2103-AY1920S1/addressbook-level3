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

    private final Catalogue catalogue;
    private final UserPrefs userPrefs;
    private final FilteredList<Book> filteredBooks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyCatalogue addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with catalogue: " + addressBook + " and user prefs " + userPrefs);

        this.catalogue = new Catalogue(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBooks = new FilteredList<>(this.catalogue.getBookList());
    }

    public ModelManager() {
        this(new Catalogue(), new UserPrefs());
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
    public Path getCatalogueFilePath() {
        return userPrefs.getCatalogueFilePath();
    }

    @Override
    public void setCatalogueFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setCatalogueFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setCatalogue(ReadOnlyCatalogue addressBook) {
        this.catalogue.resetData(addressBook);
    }

    @Override
    public ReadOnlyCatalogue getCatalogue() {
        return catalogue;
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return catalogue.hasBook(book);
    }

    @Override
    public void deleteBook(Book target) {
        catalogue.removeBook(target);
    }

    @Override
    public void addBook(Book book) {
        catalogue.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);

        catalogue.setBook(target, editedBook);
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
        return catalogue.equals(other.catalogue)
                && userPrefs.equals(other.userPrefs)
                && filteredBooks.equals(other.filteredBooks);
    }

}
