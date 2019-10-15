package seedu.address.profile;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.profile.person.Person;
import seedu.address.profile.records.Record;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    //=========== UserPrefs ==================================================================================

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

    /**
     * Returns the user prefs' DukeCooks file path.
     */
    Path getDukeCooksFilePath();

    /**
     * Sets the user prefs' Duke Cooks file path.
     */
    void setDukeCooksFilePath(Path dukeCooksFilePath);

    /**
     * Returns the user prefs' DukeCooks file path.
     */
    Path getHealthRecordsFilePath();

    /**
     * Sets the user prefs' Duke Cooks file path.
     */
    void setHealthRecordsFilePath(Path healthRecordsFilePath);

    //=========== DukeBooks ================================================================================

    /**
     * Replaces Duke Cooks data with the data in {@code dukeCooks}.
     */
    void setDukeCooks(ReadOnlyDukeCooks dukeCooks);

    /** Returns DukeCooks */
    ReadOnlyDukeCooks getDukeCooks();

    /**
     * Adds the given person.
     * {@code person} must not already exist in Duke Cooks.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in Duke Cooks.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the Duke Cooks.
     */
    void setPerson(Person target, Person editedPerson);

    //=========== Health Records ============================================================================

    /**
     * Replaces Health Records data with the data in {@code healthRecords}.
     */
    void setHealthRecords(ReadOnlyHealthRecords healthRecords);

    /** Returns Health Records */
    ReadOnlyHealthRecords getHealthRecords();

    /**
     * Adds the given record.
     * {@code record} must not already exist in Health Records.
     */
    void addRecord(Record record);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in Heath Records.
     * The record identity of {@code editedRecord} must not be the same as another existing record in Health Records.
     */
    void setRecord(Record target, Record editedRecord);


    //=========== Filtered Person List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //=========== Filtered Record List Accessors =============================================================

    /** Returns an unmodifiable view of the filtered record list */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);
}
