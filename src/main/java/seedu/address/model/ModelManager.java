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
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Incident> filteredIncidents;
    private final FilteredList<Vehicle> filteredVehicles;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        session = new Session(null);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredIncidents = new FilteredList<>(this.addressBook.getIncidentList());
        filteredVehicles = new FilteredList<>(this.addressBook.getVehicleList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void setSession(Person person) {
        session = new Session(person);
    }

    @Override
    public Session getSession() {
        return session;
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
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
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

    @Override
    public void updateFilteredIncidentList(Predicate<Incident> predicate) {
        requireNonNull(predicate);
        filteredIncidents.setPredicate(predicate);
    }


    //=========== Incidents ================================================================================

    @Override
    public boolean hasIncident(Incident incident) {
        requireNonNull(incident);
        return addressBook.hasIncident(incident);
    }
    @Override
    public void setIncident(Incident target, Incident editedIncident) {
        requireAllNonNull(target, editedIncident);
        addressBook.setIncident(target, editedIncident);

    }

    @Override
    public void addIncident(Incident incident) {
        addressBook.addIncident(incident);
        updateFilteredIncidentList(PREDICATE_SHOW_ALL_INCIDENTS);
    }

    //=========== Filtered Incident List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Incident> getFilteredIncidentList() {
        return filteredIncidents;
    }

    /*
    @Override
    public void updateFilteredIncidentList(Predicate<Incident> predicate) {
        requireNonNull(predicate);
        filteredIncidents.setPredicate(predicate);
    }*/

    //=========== Filtered Vehicle List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Vehicle} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Vehicle> getFilteredVehicleList() {
        return filteredVehicles;
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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
