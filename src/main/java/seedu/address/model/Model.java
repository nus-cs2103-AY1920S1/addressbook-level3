package seedu.address.model;

import java.nio.file.Path;
import java.util.Date;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} for persons that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} for incidents that always evaluate to true */
    Predicate<Incident> PREDICATE_SHOW_ALL_INCIDENTS = unused -> true;

    /**
     * Sets the {@code Person} that is logged into the {@code Session}.
     */
    void setSession(Person person);

    /**
     * Gets the {@code Person} that is logged into the {@code Session}.
     */
    Person getLoggedInPerson();

    /**
     * Gets the {@code Person} that is logged into the {@code Session}.
     */
    Date getLoginTime();

    /**
     * Returns true if a user is logged in.
     */
    boolean isLoggedIn();

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
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

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

    /**
     * Replaces the given incident {@code target} with {@code editedIncident}.
     * {@code target} must exit in the address book.
     * Incident details of {@code target} must not be the same as another existing incident in address book.
     */
    void setIncident(Incident target, Incident editedIncident);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns true if an incident with the same identity as {@code incident} exists in the address book.
     */
    boolean hasIncident(Incident incident);

    /**
     * Adds the given incident.
     * {@code incident} must not already exist in the address book.
     */
    void addIncident(Incident incident);

    /** Returns an unmodifiable view of the filtered incident list */
    ObservableList<Incident> getFilteredIncidentList();

    /** Returns an unmodifiable view of the filtered vehicle list */
    ObservableList<Vehicle> getFilteredVehicleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered Incident list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredIncidentList(Predicate<Incident> predicate);
}
