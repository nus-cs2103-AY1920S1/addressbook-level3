package cs.f10.t1.nursetraverse.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.appointment.AppointmentSortedByDateTime;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PATIENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /** {@code Comparator} that returns an instance of an Appointment Comparator that compares by start date and time */
    Comparator<Appointment> COMPARATOR_APPOINTMENTS_SORTED_BY_DATE_AND_TIME = new AppointmentSortedByDateTime();

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
     * Returns the user prefs' patient book file path.
     */
    Path getPatientBookFilePath();

    /**
     * Sets the user prefs' patient book file path.
     */
    void setPatientBookFilePath(Path patientBookFilePath);

    /**
     * Returns the user prefs' appointment book file path.
     */
    Path getAppointmentBookFilePath();

    /**
     * Sets the user prefs' appointment book file path.
     */
    void setAppointmentBookFilePath(Path appointmentBookFilePath);

    /**
     * Replaces patient book data with the data in {@code patientBook}.
     */
    void setStagedPatientBook(ReadOnlyPatientBook patientBook);

    /**
     * Replaces all patients in patient book with new patients from the list.
     */
    void replaceStagedPatientBook(List<Patient> patients);

    /** Returns the current PatientBook */
    ReadOnlyPatientBook getStagedPatientBook();

    /**
     * Record a new ongoing visit of patient in the model.
     * This will be saved until the visit is finished.
     * Ongoing visit must be from a Patient unmodified or an IllegalArgumentException will be thrown,
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
     * Cancel the ongoing visit if there is an ongoing visit.
     */
    void cancelOngoingVisit();

    /**
     * Set the ongoing visit of patient in the model to null.
     */
    void unsetOngoingVisit();

    /**
     * Get optional pair of current patient and visit if there is an ongoing visit.
     */
    Optional<Visit> getOngoingVisit();

    /**
     * Return true if the patient has an ongoing visit.
     * Note: The current implementation only checks if this patient is the one being tracked using the
     * currentPatientAndVisit.
     */
    boolean patientHasOngoingVisit(Patient patient);

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the patient book.
     */
    boolean hasPatient(Patient patient);

    /**
     * Returns true if any patient in the provided list has the same identity as
     * any patient in the patient book.
     */
    boolean hasAnyPatientInGivenList(List<Patient> patients);

    /**
     * Deletes the given patient.
     * The patient must exist in the patient book.
     */
    void deletePatient(Patient target);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the patient book.
     */
    void addPatient(Patient patient);

    /**
     * Adds all patients in the given list of patients.
     * {@code patient} in the list must not exist in the patient book.
     */
    void addPatients(List<Patient> patients);

    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     * {@code target} must exist in the patient book.
     * The patient identity of {@code editedPatient} must not be the same as another
     * existing patient in the patient book.
     */
    void setPatient(Patient target, Patient editedPatient);

    /** Returns the patient at the specified index */
    Patient getPatientByIndex(Index index);

    /** Returns a list of patients at the provided indexes */
    ObservableList<Patient> getPatientsByIndexes(Set<Index> indexes);

    /** Returns an unmodifiable view of the entire patient list */
    ObservableList<Patient> getStagedPatientList();

    /** Returns an unmodifiable view of the filtered patient list */
    FilteredList<Patient> getFilteredPatientList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    /**
     * Returns an unmodifiable view of the list of ongoing visits.
     * The current constraint is only one ongoing visit at one time.
     */
    ObservableList<Visit> getObservableOngoingVisitList();

    /**
     * Returns true if there are changes to the patient book that have not been {@code commit()}ed.
     * @return true if there are uncommitted changes
     */
    boolean hasStagedChanges();

    /**
     * Commits the changes made to the patient book since the last call to this method, making them permanent and
     * updating the UI data. The committing {@code MutatorCommand} is stored for history record purposes.
     * @param command the {@code MutatorCommand} making this commit
     */
    void commit(MutatorCommand command);

    /** Discards staged but uncommitted changes */
    void discardStagedChanges();

    /**
     * Reverts current model state to the {@link PatientBook} contained in the specified {@link HistoryRecord}
     * (i.e. the state before the {@link MutatorCommand} was executed).
     *
     * @param record record to revert to
     * @return list of reverted records with the first reversion at index 0
     */
    List<HistoryRecord> undoTo(HistoryRecord record);

    /**
     * Redoes the previous {@link MutatorCommand} if it was an undo.
     *
     * @return record describing the redone command and the state after its execution
     * @throws IllegalStateException if the previous command was not an undo
     */
    HistoryRecord redo() throws IllegalStateException;

    /** Returns an unmodifiable view of the history */
    ObservableList<HistoryRecord> getHistory();

    // Appointment related methods

    /**
     * Replaces appointment book data with the data in {@code appointmentBook}.
     */
    void setStagedAppointmentBook(ReadOnlyAppointmentBook appointmentBook);

    /**
     * Replaces all appointments in appointment book with new appointments from the list.
     */
    void replaceStagedAppointmentBook(List<Appointment> appointments);

    /** Returns the current AppointmentBook */
    ReadOnlyAppointmentBook getStagedAppointmentBook();

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the appointment list.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Returns true if an appointment has clashing time as {@code appointment} exists in the appointment list.
     */
    boolean hasClashingAppointment(Appointment appointment);

    /**
     * Deletes the given appointment.
     * The appointment must exist in the appointment list.
     */
    void deleteAppointment(Appointment target);

    /**
     * Deletes the given recurring appointment.
     * Unlike usual delete command, this does not add the next recurring appointment, but permanently deletes it.
     * The appointment must exist in the appointment list.
     */
    void deleteRecurringAppointment(Appointment target);

    /**
     * Deletes all appointments associated with the {@code target} patient.
     */
    void deleteAppointments(Patient target, Index targetIndex);

    /**
     * Adds the given appointment.
     * {@code appointment} must not already exist in the appointment list.
     */
    void addAppointment(Appointment appointment);

    /**
     * Replaces the given appointment {@code target} with {@code editedAppointment}.
     * {@code target} must exist in the appointment list.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing appointment
     * in the appointment list.
     */
    void setAppointment(Appointment target, Appointment editedAppointment);

    /**
     * Replaces all appointments' patients that equal {@code patientToEdit} with {@code editedPatient}.
     */
    void setAppointments(Patient patientToEdit, Patient editedPatient);

    /** Returns an unmodifiable view of the entire appointment list */
    ObservableList<Appointment> getStagedAppointmentList();

    /** Returns an unmodifiable view of the filtered appointment list */
    FilteredList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);
}
