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
import seedu.address.model.answerable.Answerable;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Answerable> filteredAnswerables;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredAnswerables = new FilteredList<>(this.addressBook.getAnswerableList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasAnswerable(Answerable answerable) {
        requireNonNull(answerable);
        return addressBook.hasAnswerable(answerable);
    }

    @Override
    public void deleteAnswerable(Answerable target) {
        addressBook.removeAnswerable(target);
    }

    @Override
    public void addAnswerable(Answerable answerable) {
        addressBook.addAnswerable(answerable);
        updateFilteredAnswerableList(PREDICATE_SHOW_ALL_ANSWERABLE);
    }

    @Override
    public void setAnswerable(Answerable target, Answerable editedAnswerable) {
        requireAllNonNull(target, editedAnswerable);

        addressBook.setAnswerable(target, editedAnswerable);
    }

    //=========== Filtered Answerable List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Answerable} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Answerable> getFilteredAnswerableList() {
        return filteredAnswerables;
    }

    @Override
    public void updateFilteredAnswerableList(Predicate<Answerable> predicate) {
        requireNonNull(predicate);
        filteredAnswerables.setPredicate(predicate);
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredAnswerables.equals(other.filteredAnswerables);
    }

}
