package seedu.billboard.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.model.person.Record;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Record> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code billboard}.
     */
    void setBillboard(ReadOnlyBillboard billboard);

    /** Returns the Billboard */
    ReadOnlyBillboard getBillboard();

    /**
     * Returns true if a record with the same identity as {@code record} exists in the address book.
     */
    boolean hasRecord(Record record);

    /**
     * Deletes the given record.
     * The record must exist in the address book.
     */
    void deletePerson(Record target);

    /**
     * Adds the given record.
     * {@code record} must not already exist in the address book.
     */
    void addPerson(Record expense);

    /**
     * Replaces the given record {@code target} with {@code editedRecord}.
     * {@code target} must exist in the address book.
     * The record {@code editedRecord} must not be the same as another existing record in the address book.
     */
    void setPerson(Record target, Record editedRecord);

    /** Returns an unmodifiable view of the filtered record list */
    ObservableList<Record> getFilteredPersonList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Record> predicate);
}
