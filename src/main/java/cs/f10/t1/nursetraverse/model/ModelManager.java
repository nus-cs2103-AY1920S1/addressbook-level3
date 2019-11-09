package cs.f10.t1.nursetraverse.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.commons.core.LogsCenter;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * Represents the in-memory model of the patient book data.
 */
public class ModelManager implements Model {
    private static final int MAX_HISTORY_SIZE = 20;

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private PatientBook basePatientBook;
    private PatientBook stagedPatientBook;
    private final HistoryManager historyManager;
    private final UserPrefs userPrefs;
    // Modifiable list containing current stagedPatientBook patients
    private final ObservableList<Patient> stagedPatients;
    private final FilteredList<Patient> filteredPatients; // Unmodifiable view for the UI linked to stagedPatients

    //Placing ongoingVisitList here so that any changes to the ongoing visit will be reflected
    //in the UI
    private final ObservableList<Visit> ongoingVisitList;

    //Previous predicate variable to keep track of the predicate used by FindCommands
    private Predicate<Patient> previousPredicatePatients = PREDICATE_SHOW_ALL_PATIENTS;

    //Appointment list

    private AppointmentBook baseAppointmentBook;
    private AppointmentBook stagedAppointmentBook;
    // Modifiable list containing current stagedAppointmentBook patients
    private final ObservableList<Appointment> stagedAppointments;
    // Unmodifiable view for the UI linked to stagedAppointments
    private final FilteredList<Appointment> filteredAppointments;
    //Previous predicate variable to keep track of the predicate used by FindCommands
    private Predicate<Appointment> previousPredicateAppointments = PREDICATE_SHOW_ALL_APPOINTMENTS;

    /**
     * Initializes a ModelManager with the given patientBook and userPrefs and appointmentBook.
     */
    public ModelManager(ReadOnlyPatientBook patientBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyAppointmentBook appointmentBook) {
        super();
        CollectionUtil.requireAllNonNull(patientBook, userPrefs, appointmentBook);

        logger.fine("Initializing with patient book: " + patientBook + " and user prefs " + userPrefs
                    + " and appointment book: " + appointmentBook);

        this.basePatientBook = new PatientBook(patientBook);
        this.stagedPatientBook = this.basePatientBook.deepCopy();
        this.baseAppointmentBook = new AppointmentBook(appointmentBook);
        this.stagedAppointmentBook = this.baseAppointmentBook.deepCopy();

        this.historyManager = new HistoryManager(MAX_HISTORY_SIZE);
        this.userPrefs = new UserPrefs(userPrefs);

        stagedPatients = FXCollections.observableArrayList();
        filteredPatients = new FilteredList<>(FXCollections.unmodifiableObservableList(stagedPatients));

        //Initializing ongoingVisitList here instead of in PatientBook as it is a wrapper of the data
        ongoingVisitList = FXCollections.observableArrayList();
        Optional<Visit> ongoingVisit = this.stagedPatientBook.getOngoingVisit();
        ongoingVisit.ifPresent(ongoingVisitList::add);
        refreshStagedData();

        //Initializing appointment related book and appointments
        stagedAppointments = FXCollections.observableArrayList();
        filteredAppointments = new FilteredList<>(FXCollections.unmodifiableObservableList(stagedAppointments));
        refreshStagedAppointments();
    }

    public ModelManager() {
        this(new PatientBook(), new UserPrefs(), new AppointmentBook());
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
    public Path getPatientBookFilePath() {
        return userPrefs.getPatientBookFilePath();
    }

    @Override
    public void setPatientBookFilePath(Path patientBookFilePath) {
        requireNonNull(patientBookFilePath);
        userPrefs.setPatientBookFilePath(patientBookFilePath);
    }

    @Override
    public Path getAppointmentBookFilePath() {
        return userPrefs.getAppointmentBookFilePath();
    }

    @Override
    public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        userPrefs.setAppointmentBookFilePath(appointmentBookFilePath);
    }

    //=========== PatientBook ================================================================================

    @Override
    public void setStagedPatientBook(ReadOnlyPatientBook patientBook) {
        this.stagedPatientBook.resetData(patientBook);
        refreshStagedData();
    }

    @Override
    public void replaceStagedPatientBook(List<Patient> patients) {
        PatientBook newBook = new PatientBook();
        for (Patient patient : patients) {
            newBook.addPatient(patient);
        }
        setStagedPatientBook(newBook);
        refreshStagedData();
    }

    @Override
    public ReadOnlyPatientBook getStagedPatientBook() {
        return stagedPatientBook;
    }

    /**
     * Record ongoing visit of patient in the model.
     * This will be saved until the visit is finished.
     */
    @Override
    public void setNewOngoingVisit(Visit visit) {
        requireNonNull(visit);
        stagedPatientBook.setOngoingVisit(visit);
        updateOngoingVisitList();
    }

    @Override
    public void cancelOngoingVisit() {
        Optional<Visit> optionalVisit = getOngoingVisit();
        if (optionalVisit.isPresent()) {
            unsetOngoingVisit();
            Visit visit = optionalVisit.get();
            visit.getPatient().removeVisit(visit, this);
        }
    }

    @Override
    public void unsetOngoingVisit() {
        stagedPatientBook.unsetOngoingVisit();
        updateOngoingVisitList();
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

    /**
     * Helper method to update ongoing visit list which is linked to the UI
     */
    private void updateOngoingVisitList() {
        ongoingVisitList.clear();
        Optional<Visit> potentialOngoingVisit = this.stagedPatientBook.getOngoingVisit();
        if (potentialOngoingVisit.isPresent()) {
            ongoingVisitList.add(potentialOngoingVisit.get());
        }
    }

    @Override
    public Optional<Visit> getOngoingVisit() {
        return stagedPatientBook.getOngoingVisit();
    }

    @Override
    public boolean patientHasOngoingVisit(Patient patient) {
        requireNonNull(patient);
        Optional<Visit> optionalVisit = getOngoingVisit();
        return optionalVisit.isPresent()
                && patient.equals(optionalVisit.get().getPatient());
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return stagedPatientBook.hasPatient(patient);
    }

    @Override
    public boolean hasAnyPatientInGivenList(List<Patient> patients) {
        requireNonNull(patients);
        return patients.stream().anyMatch(this::hasPatient);
    }

    @Override
    public void deletePatient(Patient target) {
        stagedPatientBook.removePatient(target);
        refreshStagedData();
        refreshFilteredPatientList();
    }

    @Override
    public void addPatient(Patient patient) {
        stagedPatientBook.addPatient(patient);
        refreshStagedData();
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void addPatients(List<Patient> patients) {
        requireNonNull(patients);
        assert !patients.isEmpty();
        for (Patient patient : patients) {
            stagedPatientBook.addPatient(patient);
        }
        refreshStagedData();
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        CollectionUtil.requireAllNonNull(target, editedPatient);

        stagedPatientBook.setPatient(target, editedPatient);
        refreshStagedData();
    }

    @Override
    public Patient getPatientByIndex(Index index) {
        requireNonNull(index);
        assert index.getZeroBased() <= stagedPatients.size();

        return stagedPatientBook.getPatientByIndex(index);
    }

    @Override
    public ObservableList<Patient> getPatientsByIndexes(Set<Index> indexes) {
        requireNonNull(indexes);

        return stagedPatientBook.getPatientListByIndexes(indexes);
    }

    // Undo/Redo/History specific methods
    //@@author gabrielchao

    /**
     * Returns an unmodifiable view of the full list of {@code Patient} backed by {@code stagedPatients}
     */
    @Override
    public ObservableList<Patient> getStagedPatientList() {
        return new FilteredList<>(FXCollections.unmodifiableObservableList(stagedPatients));
    }

    @Override
    public boolean hasStagedChanges() {
        return !basePatientBook.equals(stagedPatientBook) || !baseAppointmentBook.equals(stagedAppointmentBook);
    }

    @Override
    public void commit(MutatorCommand command) {
        historyManager.pushRecord(command, basePatientBook, baseAppointmentBook);
        changeBaseTo(stagedPatientBook, stagedAppointmentBook);
    }

    @Override
    public void discardStagedChanges() {
        stagedPatientBook = basePatientBook.deepCopy();
        stagedAppointmentBook = baseAppointmentBook.deepCopy();
        refreshStagedData();
        refreshStagedAppointments();
    }

    @Override
    public List<HistoryRecord> undoTo(HistoryRecord record) throws NoSuchElementException {
        List<HistoryRecord> poppedRecords = historyManager.popRecordsTo(record, stagedPatientBook,
                                                                        stagedAppointmentBook);
        changeBaseTo(record.getCopyOfPatientBook(), record.getCopyOfAppointmentBook());
        return poppedRecords;
    }

    @Override
    public HistoryRecord redo() throws IllegalStateException {
        Optional<HistoryRecord> redoneRecord = historyManager.popRedo(stagedPatientBook, stagedAppointmentBook);
        if (redoneRecord.isEmpty()) {
            throw new IllegalStateException("Cannot redo: previous MutatorCommand was not an undo");
        }
        changeBaseTo(redoneRecord.get().getCopyOfPatientBook(), redoneRecord.get().getCopyOfAppointmentBook());
        return redoneRecord.get();
    }

    @Override
    public ObservableList<HistoryRecord> getHistory() {
        return historyManager.asUnmodifiableObservableList();
    }

    /**
     * Changes base patient book and appointment book to {@code patientBook} and {@code appointmentBook} respectively.
     * @param patientBook
     * @param appointmentBook
     */
    private void changeBaseTo(PatientBook patientBook, AppointmentBook appointmentBook) {
        basePatientBook = patientBook;
        stagedPatientBook = basePatientBook.deepCopy();
        baseAppointmentBook = appointmentBook;
        stagedAppointmentBook = baseAppointmentBook.deepCopy();
        refreshStagedData();
        refreshStagedAppointments();
    }

    /**
     * Refresh staged data on changing data or undo/redo. Affects stagedPatients and ongoingVisitList.
     */
    private void refreshStagedData() {
        stagedPatients.setAll(stagedPatientBook.getPatientList());
        updateOngoingVisitList();
    }

    //@@author

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by {@code stagedPatients}
     */
    @Override
    public FilteredList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    /**
     * Refreshes the filter of the filtered patient list to filter by the given {@code predicate}.
     * This will update the indexes of patients if a patient outside of the filtered list has been removed.
     */
    private void refreshFilteredPatientList() {
        //In order to refresh predicate, need to reset it to show all patients first.
        //Otherwise it will not change anything
        filteredPatients.setPredicate(PREDICATE_SHOW_ALL_PATIENTS);
        filteredPatients.setPredicate(previousPredicatePatients);
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        previousPredicatePatients = predicate;
        filteredPatients.setPredicate(predicate);
    }

    /**
     * Returns true if the current state of this {@code ModelManager} is the same as {@code obj}.
     * It does NOT take into account {@code basePatientBook} or {@code historyManager}.
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
    public boolean hasClashingAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return stagedAppointmentBook.hasClashingAppointment(appointment);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        stagedAppointmentBook.removeAppointment(target);
        refreshStagedAppointments();
        refreshFilteredAppointmentList();
    }

    @Override
    public void deleteRecurringAppointment(Appointment target) {
        stagedAppointmentBook.removeRecurringAppointment(target);
        refreshStagedAppointments();
        refreshFilteredAppointmentList();
    }

    @Override
    public void deleteAppointments(Patient target, Index targetIndex) {
        stagedAppointmentBook.removeAppointments(target, targetIndex);
        refreshStagedAppointments();
        refreshFilteredAppointmentList();
    }

    @Override
    public void addAppointment(Appointment appointment) {
        stagedAppointmentBook.addAppointment(appointment);
        refreshStagedAppointments();
        sortStagedAppointments();
        replaceStagedAppointmentBook(stagedAppointments);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        CollectionUtil.requireAllNonNull(target, editedAppointment);

        stagedAppointmentBook.setAppointment(target, editedAppointment);
        refreshStagedAppointments();
        sortStagedAppointments();
        replaceStagedAppointmentBook(stagedAppointments);
    }

    @Override
    public void setAppointments(Patient patientToEdit, Patient editedPatient) {
        CollectionUtil.requireAllNonNull(patientToEdit, editedPatient);

        stagedAppointmentBook.editAppointments(patientToEdit, editedPatient);
        refreshStagedAppointments();
    }

    private void refreshStagedAppointments() {
        stagedAppointments.setAll(stagedAppointmentBook.getAppointmentList());
    }

    private void sortStagedAppointments() {
        FXCollections.sort(stagedAppointments, COMPARATOR_APPOINTMENTS_SORTED_BY_DATE_AND_TIME);
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
        return stagedPatientBook.equals(other.stagedPatientBook)
                && stagedAppointmentBook.equals(other.stagedAppointmentBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPatients.equals(other.filteredPatients)
                && filteredAppointments.equals(other.filteredAppointments);
    }

}
