package seedu.address.model;

import java.nio.file.Path;
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

    /** {@code Predicate} for incidents that always evaluate to true */
    Predicate<Vehicle> PREDICATE_SHOW_ALL_VEHICLES = unused -> true;

    /** {@code Predicate} for filtering draft incident reports */
    Predicate<Incident> PREDICATE_SHOW_DRAFT_INCIDENT_REPORTS = Incident::isDraft;

    /** {@code Predicate} for filtering complete incident reports */
    Predicate<Incident> PREDICATE_SHOW_COMPLETE_INCIDENT_REPORTS = Incident::isCompleteDraft;

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
    String getLoginTime();

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
    Path getIncidentManagerFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setIncidentManagerFilePath(Path incidentManagerFilePath);

    /**
     * Replaces address book data with the data in {@code incidentManager}.
     */
    void setIncidentManager(ReadOnlyIncidentManager incidentManager);

    /** Returns the IncidentManager */
    ReadOnlyIncidentManager getIncidentManager();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the incident manager.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the incident manager.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the incident manager.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the incident manager.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the incident
     * manager.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given incident {@code target} with {@code editedIncident}.
     * {@code target} must exit in the incident manager.
     * Incident details of {@code target} must not be the same as another existing incident in incident manager.
     */
    void setIncident(Incident target, Incident editedIncident);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns true if an incident with the same identity as {@code incident} exists in the incident manager.
     */
    boolean hasIncident(Incident incident);

    /**
     * Adds the given incident.
     * {@code incident} must not already exist in the incident manager.
     */
    void addIncident(Incident incident);

    /**
     * Removes the given incident.
     * The {@code incident} must exist in the incident manager.
     */
    void removeIncident(Incident incident);

    /**
     * Returns true if a vehicle of the same identity as (@code vehicle} exists in the incident manager.
     */
    boolean hasVehicle(Vehicle vehicle);

    /**
     * Returns true if a vehicle of the same vehicle number exists in the Incident Manager.
     */
    boolean hasVNum(String vNum);

    /**
     * Replaces the given Vehicle {@code target} with {@code editedVehicle}.
     * {@code target} must exist in the Incident Manager.
     * The identity of {@code target} must not be the same as another vehicle in the Incident Manager
     */
    void setVehicle(Vehicle target, Vehicle editedVehicle);

    /**
     * Adds a {@code Vehicle toAdd} to the Incident Manager.
     */
    void addVehicle(Vehicle toAdd);

    /**
     * Deletes the given vehicle if {@code vehicleToDelete} exists in the incident manager.
     */
    void deleteVehicle(Vehicle vehicleToDelete);

    /** Returns an unmodifiable view of the filtered incident list */
    ObservableList<Incident> getFilteredIncidentList();

    /** Returns true if incident list contains incidents filtered by given predicate */
    boolean ifAnyIncidentsSatisfyPredicate(Predicate<Incident> predicate);

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

    /**
     * Updates the filter of the filtered Vehicle list to filter by the given {@code district}.
     * @throws NullPointerException if {@code district} is null.
     */
    void updateFilteredVehicleList(Predicate<Vehicle> predicate);
}
