package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.visit.Visit;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final int MAX_HISTORY_SIZE = 20;

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private AddressBook baseAddressBook;
    private AddressBook stagedAddressBook;
    private final HistoryManager historyManager;
    private final UserPrefs userPrefs;
    private final ObservableList<Person> stagedPersons; // Modifiable list containing current stagedAddressBook persons
    private final FilteredList<Person> filteredPersons; // Unmodifiable view for the UI linked to stagedPersons

    //Placing ongoingVisitList here so that any changes to the ongoing visit will be reflected
    //in the UI
    private final ObservableList<Visit> ongoingVisitList;

    //Previous predicate variable to keep track of the predicate used by FindCommands
    private Predicate<Person> previousPredicatePersons = PREDICATE_SHOW_ALL_PERSONS;

    //Appointment list

    private final AppointmentBook baseAppointmentBook;
    private final AppointmentBook stagedAppointmentBook;
    // Modifiable list containing current stagedAppointmentBook persons
    private final ObservableList<Appointment> stagedAppointments;
    // Unmodifiable view for the UI linked to stagedAppointments
    private final FilteredList<Appointment> filteredAppointments;
    //Previous predicate variable to keep track of the predicate used by FindCommands
    private Predicate<Appointment> previousPredicateAppointments = PREDICATE_SHOW_ALL_APPOINTMENTS;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.baseAddressBook = new AddressBook(addressBook);
        this.stagedAddressBook = this.baseAddressBook.deepCopy();
        this.historyManager = new HistoryManager(MAX_HISTORY_SIZE);
        this.userPrefs = new UserPrefs(userPrefs);

        stagedPersons = FXCollections.observableArrayList();
        filteredPersons = new FilteredList<>(FXCollections.unmodifiableObservableList(stagedPersons));
        refreshStagedPersons();

        //Initializing ongoingVisitList here instead of in AddressBook as it is a wrapper of the data
        ongoingVisitList = FXCollections.observableArrayList();
        Optional<Visit> ongoingVisit = this.stagedAddressBook.getOngoingVisit();
        ongoingVisit.ifPresent(ongoingVisitList::add);

        //Initializing appointment related book and appointments
        this.baseAppointmentBook = new AppointmentBook();
        this.stagedAppointmentBook = this.baseAppointmentBook.deepCopy();

        stagedAppointments = FXCollections.observableArrayList();
        filteredAppointments = new FilteredList<>(FXCollections.unmodifiableObservableList(stagedAppointments));
        refreshStagedAppointments();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
    public void setStagedAddressBook(ReadOnlyAddressBook addressBook) {
        this.stagedAddressBook.resetData(addressBook);
        refreshStagedPersons();
    }

    @Override
    public void replaceStagedAddressBook(List<Person> persons) {
        AddressBook newBook = new AddressBook();
        for (Person person : persons) {
            newBook.addPerson(person);
        }
        setStagedAddressBook(newBook);
        refreshStagedPersons();
    }

    @Override
    public ReadOnlyAddressBook getStagedAddressBook() {
        return stagedAddressBook;
    }

    /**
     * Record ongoing visit of person in the model.
     * This will be saved until the visit is finished.
     */
    @Override
    public void setNewOngoingVisit(Visit visit) {
        requireNonNull(visit);
        ongoingVisitList.clear();
        ongoingVisitList.add(visit);
        stagedAddressBook.setOngoingVisit(visit);
    }

    @Override
    public void unsetOngoingVisit() {
        ongoingVisitList.clear();
        stagedAddressBook.unsetOngoingVisit();
    }

    @Override
    public void updateOngoingVisit(Visit updatedVisit) {
        requireNonNull(updatedVisit);
        Optional<Visit> optionalOngoingVisit = getOngoingVisit();
        if (optionalOngoingVisit.isPresent()) {
            Visit ongoingVisit = optionalOngoingVisit.get();
            ongoingVisit.getPatient().updateVisit(ongoingVisit, updatedVisit);
            setNewOngoingVisit(updatedVisit);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public Optional<Visit> getOngoingVisit() {
        return stagedAddressBook.getOngoingVisit();
    }

    @Override
    public boolean patientHasOngoingVisit(Person patientToDelete) {
        requireNonNull(patientToDelete);
        Optional<Visit> optionalVisit = getOngoingVisit();
        return optionalVisit.isPresent()
                && patientToDelete.equals(optionalVisit.get().getPatient());
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return stagedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        stagedAddressBook.removePerson(target);
        refreshStagedPersons();
        refreshFilteredPersonList();
    }

    @Override
    public void addPerson(Person person) {
        stagedAddressBook.addPerson(person);
        refreshStagedPersons();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        stagedAddressBook.setPerson(target, editedPerson);
    }

    /**
     * Returns an unmodifiable view of the full list of {@code Person} backed by {@code stagedPersons}
     */
    @Override
    public ObservableList<Person> getStagedPersonList() {
        return new FilteredList<>(FXCollections.unmodifiableObservableList(stagedPersons));
    }

    @Override
    public boolean hasStagedChanges() {
        return !baseAddressBook.equals(stagedAddressBook);
    }

    @Override
    public void commit(MutatorCommand command) {
        historyManager.pushRecord(command, baseAddressBook);
        changeBaseTo(stagedAddressBook);
    }

    @Override
    public void discardStagedChanges() {
        stagedAddressBook = baseAddressBook.deepCopy();
        refreshStagedPersons();
    }

    @Override
    public void revertTo(HistoryRecord record) throws NoSuchElementException {
        historyManager.popRecordsTo(record);
        changeBaseTo(record.getCopyOfAddressBook());
    }

    @Override
    public ObservableList<HistoryRecord> getHistory() {
        return historyManager.asUnmodifiableObservableList();
    }

    private void changeBaseTo(AddressBook addressBook) {
        baseAddressBook = addressBook;
        stagedAddressBook = baseAddressBook.deepCopy();
        refreshStagedPersons();
    }

    private void refreshStagedPersons() {
        stagedPersons.setAll(stagedAddressBook.getPersonList());
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by {@code stagedPersons}
     */
    @Override
    public FilteredList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Refreshes the filter of the filtered patient list to filter by the given {@code predicate}.
     * This will update the indexes of patients if a patient outside of the filtered list has been removed.
     */
    private void refreshFilteredPersonList() {
        //In order to refresh predicate, need to reset it to show all persons first.
        //Otherwise it will not change anything
        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        filteredPersons.setPredicate(previousPredicatePersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        previousPredicatePersons = predicate;
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Returns true if the current state of this {@code ModelManager} is the same as {@code obj}.
     * It does NOT take into account {@code baseAddressBook} or {@code historyManager}.
     */
    @Override
    public ObservableList<Visit> getObservableOngoingVisitList() {
        return FXCollections.unmodifiableObservableList(ongoingVisitList);
    }

    // Appointment related method implementations

    //=========== AppointmentBook ================================================================================

    @Override
    public void setStagedAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
        this.stagedAppointmentBook.resetData(appointmentBook);
        refreshStagedAppointments();
    }

    @Override
    public void replaceStagedAppointmentBook(List<Appointment> appointments) {
        AppointmentBook newBook = new AppointmentBook();
        for (Appointment appointment : appointments) {
            newBook.addAppointment(appointment);
        }
        setStagedAppointmentBook(newBook);
        refreshStagedAppointments();
    }

    @Override
    public ReadOnlyAppointmentBook getStagedAppointmentBook() {
        return stagedAppointmentBook;
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return stagedAppointmentBook.hasAppointment(appointment);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        stagedAppointmentBook.removeAppointment(target);
        refreshStagedAppointments();
        refreshFilteredAppointmentList();
    }

    @Override
    public void addAppointment(Appointment appointment) {
        stagedAppointmentBook.addAppointment(appointment);
        refreshStagedAppointments();
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        stagedAppointmentBook.setAppointment(target, editedAppointment);
    }

    private void refreshStagedAppointments() {
        stagedAppointments.setAll(stagedAppointmentBook.getAppointmentList());
    }

    /**
     * Returns an unmodifiable view of the full list of {@code Appointment} backed by {@code stagedAppointments}
     */
    @Override
    public ObservableList<Appointment> getStagedAppointmentList() {
        return new FilteredList<>(FXCollections.unmodifiableObservableList(stagedAppointments));
    }

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by {@code stagedAppointments}
     */
    @Override
    public FilteredList<Appointment> getFilteredAppointmentList() {
        return filteredAppointments;
    }

    /**
     * Refreshes the filter of the filtered appointment list to filter by the given {@code predicate}.
     * This will update the indexes of appointments if an appointment outside of the filtered list has been removed.
     */
    private void refreshFilteredAppointmentList() {
        //In order to refresh predicate, need to reset it to show all appointments first.
        //Otherwise it will not change anything
        filteredAppointments.setPredicate(PREDICATE_SHOW_ALL_APPOINTMENTS);
        filteredAppointments.setPredicate(previousPredicateAppointments);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        previousPredicateAppointments = predicate;
        filteredAppointments.setPredicate(predicate);
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
        return stagedAddressBook.equals(other.stagedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
