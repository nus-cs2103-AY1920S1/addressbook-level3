package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.Body;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the address book data.
 */
public class BodyModelManager implements BodyModel {
    private static final Logger logger = LogsCenter.getLogger(BodyModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Body> filteredBodies;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public BodyModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBodies = new FilteredList<>(this.addressBook.getBodyList());
    }

    public BodyModelManager() {
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
    public boolean hasBody(Body body) {
        requireNonNull(body);
        return addressBook.hasBody(body);
    }

    @Override
    public void deleteBody(Body target) {
        addressBook.removeBody(target);
    }

    @Override
    public void addBody(Body body) {
        addressBook.addBody(body);
        updateFilteredBodyList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setBody(Body target, Body editedBody) {
        requireAllNonNull(target, editedBody);

        addressBook.setBody(target, editedBody);
    }

    //=========== Filtered Body List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Body} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Body> getFilteredBodyList() {
        return filteredBodies;
    }

    @Override
    public void updateFilteredBodyList(Predicate<Body> predicate) {
        requireNonNull(predicate);
        filteredBodies.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof BodyModelManager)) {
            return false;
        }

        // state check
        BodyModelManager other = (BodyModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredBodies.equals(other.filteredBodies);
    }

}
