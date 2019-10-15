package seedu.address.profile;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.profile.person.Person;
import seedu.address.profile.records.Record;

/**
 * Represents the in-memory model of Duke Cooks data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final DukeCooks dukeCooks;
    private final HealthRecords healthRecords;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Record> filteredRecords;

    /**
     * Initializes a ModelManager with the given dukeCooks and userPrefs.
     */
    public ModelManager(ReadOnlyDukeCooks dukeCooks, ReadOnlyHealthRecords healthRecords,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(dukeCooks, healthRecords, userPrefs);

        logger.fine("Initializing with Duke Cooks: " + dukeCooks
                + "with Health Records: " + healthRecords
                + "and user prefs " + userPrefs);

        this.dukeCooks = new DukeCooks(dukeCooks);
        this.healthRecords = new HealthRecords(healthRecords);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.dukeCooks.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
    }

    public ModelManager() {
        this(new DukeCooks(), new HealthRecords(), new UserPrefs());
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
    public Path getDukeCooksFilePath() {
        return userPrefs.getDukeCooksFilePath();
    }

    @Override
    public void setDukeCooksFilePath(Path dukeCooksFilePath) {
        requireNonNull(dukeCooksFilePath);
        userPrefs.setDukeCooksFilePath(dukeCooksFilePath);
    }

    @Override
    public Path getHealthRecordsFilePath() {
        return userPrefs.getHealthRecordsFilePath();
    }

    @Override
    public void setHealthRecordsFilePath(Path healthRecordsFilePath) {
        requireNonNull(healthRecordsFilePath);
        userPrefs.setHealthRecordsFilePath(healthRecordsFilePath);
    }

    //=========== DukeBooks ================================================================================

    @Override
    public void setDukeCooks(ReadOnlyDukeCooks dukeCooks) {
        this.dukeCooks.resetData(dukeCooks);
    }

    @Override
    public ReadOnlyDukeCooks getDukeCooks() {
        return dukeCooks;
    }

    @Override
    public void addPerson(Person person) {
        dukeCooks.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        dukeCooks.setPerson(target, editedPerson);
    }

    //=========== Health Records ============================================================================

    @Override
    public void setHealthRecords(ReadOnlyHealthRecords healthRecords) {
        this.healthRecords.resetData(healthRecords);
    }

    @Override
    public ReadOnlyHealthRecords getHealthRecords() {
        return healthRecords;
    }

    @Override
    public void addRecord(Record record) {
        healthRecords.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
    }

    @Override
    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);

        healthRecords.setRecord(target, editedRecord);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedDukeCooks}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Record List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Record} backed by the internal list of
     * {@code versionedDukeCooks}
     */
    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return filteredRecords;
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredRecords.setPredicate(predicate);
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
        return dukeCooks.equals(other.dukeCooks)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
