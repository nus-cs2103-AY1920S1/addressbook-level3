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
import seedu.address.model.incident.Incident;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.Vehicle;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private Session session;
    private final IncidentManager incidentManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Incident> filteredIncidents;
    private final FilteredList<Vehicle> filteredVehicles;

    /**
     * Initializes a ModelManager with the given incidentManager and userPrefs.
     */
    public ModelManager(ReadOnlyIncidentManager incidentManager, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(incidentManager, userPrefs);

        logger.fine("Initializing with address book: " + incidentManager + " and user prefs " + userPrefs);

        this.incidentManager = new IncidentManager(incidentManager);
        this.userPrefs = new UserPrefs(userPrefs);
        session = new Session(null);
        filteredPersons = new FilteredList<>(this.incidentManager.getPersonList());
        filteredIncidents = new FilteredList<>(this.incidentManager.getIncidentList());
        filteredVehicles = new FilteredList<>(this.incidentManager.getVehicleList());
    }

    public ModelManager() {
        this(new IncidentManager(), new UserPrefs());
    }

    @Override
    public void setSession(Person person) {
        if (person != null) {
            logger.info("Session started by " + person.getUsername());
        } else {
            logger.info("Session Reset");
        }
        session = new Session(person);
    }

    @Override
    public Person getLoggedInPerson() {
        return session.getLoggedInPerson();
    }

    @Override
    public String getLoginTime() {
        return session.getLoginTime();
    }

    @Override
    public boolean isLoggedIn() {
        return session.getLoggedInPerson() != null;
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
    public Path getIncidentManagerFilePath() {
        return userPrefs.getIncidentManagerFilePath();
    }

    @Override
    public void setIncidentManagerFilePath(Path incidentManagerFilePath) {
        requireNonNull(incidentManagerFilePath);
        userPrefs.setIncidentManagerFilePath(incidentManagerFilePath);
    }

    //=========== IncidentManager ================================================================================

    @Override
    public void setIncidentManager(ReadOnlyIncidentManager incidentManager) {
        this.incidentManager.resetData(incidentManager);
    }

    @Override
    public ReadOnlyIncidentManager getIncidentManager() {
        return incidentManager;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return incidentManager.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        incidentManager.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        incidentManager.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        incidentManager.setPerson(target, editedPerson);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedIncidentManager}
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



    //=========== Incidents ================================================================================

    @Override
    public boolean hasIncident(Incident incident) {
        requireNonNull(incident);
        return incidentManager.hasIncident(incident);
    }
    @Override
    public void setIncident(Incident target, Incident editedIncident) {
        requireAllNonNull(target, editedIncident);
        incidentManager.setIncident(target, editedIncident);
    }

    @Override
    public void addIncident(Incident incident) {
        incidentManager.addIncident(incident);
        updateFilteredIncidentList(PREDICATE_SHOW_ALL_INCIDENTS);
    }

    @Override
    public void removeIncident(Incident incident) {
        incidentManager.removeIncident(incident);
    }

    //=========== Filtered Incident List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Incident} backed by the internal list of
     * {@code versionedIncidentManager}
     */
    @Override
    public ObservableList<Incident> getFilteredIncidentList() {
        return filteredIncidents;
    }

    @Override
    public void updateFilteredIncidentList(Predicate<Incident> predicate) {
        requireNonNull(predicate);
        filteredIncidents.setPredicate(predicate);
    }

    /**
     * Checks if the list contains incidents satisfying a given predicate.
     * @return true if the list contains incidents satisfying given predicate, false otherwise.
     */
    public boolean ifAnyIncidentsSatisfyPredicate(Predicate<Incident> predicate) {
        return !filteredIncidents.filtered(predicate).isEmpty();
    }

    //=========== Vehicles ================================================================================

    @Override
    public boolean hasVehicle(Vehicle vehicle) {
        requireNonNull(vehicle);
        return incidentManager.hasVehicle(vehicle);
    }

    @Override
    public void setVehicle(Vehicle target, Vehicle editedVehicle) {
        requireAllNonNull(target, editedVehicle);
        incidentManager.setVehicle(target, editedVehicle);
    }

    @Override
    public void addVehicle(Vehicle toAdd) {
        requireNonNull(toAdd);
        incidentManager.addVehicle(toAdd);
    }

    @Override
    public void deleteVehicle(Vehicle toDelete) {
        requireNonNull(toDelete);
        incidentManager.deleteVehicle(toDelete);
    }
    //=========== Filtered Vehicle List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Vehicle} backed by the internal list of
     * {@code versionedIncidentManager}
     */
    @Override
    public ObservableList<Vehicle> getFilteredVehicleList() {
        return filteredVehicles;
    }

    @Override
    public void updateFilteredVehicleList(Predicate<Vehicle> predicate) {
        requireNonNull(predicate);
        filteredVehicles.setPredicate(predicate);
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
        return incidentManager.equals(other.incidentManager)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
