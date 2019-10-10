package seedu.billboard.model;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.model.person.Record;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Billboard billboard;
    private final UserPrefs userPrefs;
    private final FilteredList<Record> filteredExpenses;

    /**
     * Initializes a ModelManager with the given billboard and userPrefs.
     */
    public ModelManager(ReadOnlyBillboard billboard, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(billboard, userPrefs);

        logger.fine("Initializing with address book: " + billboard + " and user prefs " + userPrefs);

        this.billboard = new Billboard(billboard);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredExpenses = new FilteredList<>(this.billboard.getPersonList());
    }

    public ModelManager() {
        this(new Billboard(), new UserPrefs());
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

    //=========== Billboard ================================================================================

    @Override
    public void setBillboard(ReadOnlyBillboard billboard) {
        this.billboard.resetData(billboard);
    }

    @Override
    public ReadOnlyBillboard getBillboard() {
        return billboard;
    }

    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return billboard.hasPerson(record);
    }

    @Override
    public void deletePerson(Record target) {
        billboard.removePerson(target);
    }

    @Override
    public void addPerson(Record expense) {
        billboard.addPerson(expense);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        billboard.setPerson(target, editedRecord);
    }

    //=========== Filtered Expense List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Record> getFilteredPersonList() {
        return filteredExpenses;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredExpenses.setPredicate(predicate);
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
        return billboard.equals(other.billboard)
                && userPrefs.equals(other.userPrefs)
                && filteredExpenses.equals(other.filteredExpenses);
    }

}
