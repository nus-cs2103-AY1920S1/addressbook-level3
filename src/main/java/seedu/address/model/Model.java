package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setStagedAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Replaces all persons in address book with new persons from the list.
     */
    void replaceStagedAddressBook(List<Person> persons);
    /** Returns the current AddressBook */
    ReadOnlyAddressBook getStagedAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the entire person list */
    ObservableList<Person> getStagedPersonList();

    /** Returns an unmodifiable view of the filtered person list */
    FilteredList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if there are changes to the address book that have not been {@code commit()}ed.
     * @return true if there are uncommitted changes
     */
    boolean hasStagedChanges();

    /**
     * Commits the changes made to the address book since the last call to this method, making them permanent and
     * updating the UI data. The committing {@code MutatorCommand} is stored for history record purposes.
     * @param command the {@code MutatorCommand} making this commit
     */
    void commit(MutatorCommand command);

    /** Discards staged but uncommitted changes */
    void discardStagedChanges();

    /**
     * Reverts current model state to the {@link AddressBook} contained in the specified {@link HistoryRecord}
     * (i.e. the state before the {@link MutatorCommand} was executed).
     */
    void revertTo(HistoryRecord record);

    /** Returns an unmodifiable view of the history */
    ObservableList<HistoryRecord> getHistory();
}
