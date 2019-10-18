package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;

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
     * Record a new ongoing visit of person in the model.
     * This will be saved until the visit is finished.
     * Ongoing visit must be from a Patient unmodified for this to work without throwing an exception,
     * so only use this to begin visits.
     */
    void setNewOngoingVisit(Visit visit);

    /**
     * Update an ongoing visit in the model. This will update the ongoing visit
     * AND update the visit in the patient.
     * Use this to update an ongoing visit when there is already a visit.
     */
    void updateOngoingVisit(Visit updatedVisit);

    /**
     * Set the ongoing visit of person in the model to null.
     */
    void unsetOngoingVisit();

    /**
     * Get optional pair of current person and visit if there is an ongoing visit.
     */
    Optional<Visit> getOngoingVisit();

    /**
     * Return true if the person has an ongoing visit.
     * Note: The current implementation only checks if this person is the one being tracked using the
     * currentPersonAndVisit.
     */
    boolean patientHasOngoingVisit(Person personToDelete);

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
     * Returns an unmodifiable view of the list of ongoing visits.
     * The current constraint is only one ongoing visit at one time.
     */
    ObservableList<Visit> getObservableOngoingVisitList();

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
