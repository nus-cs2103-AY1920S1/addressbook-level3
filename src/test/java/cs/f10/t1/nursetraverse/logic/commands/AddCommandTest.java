package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyAppointmentBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyUserPrefs;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.testutil.Assert;
import cs.f10.t1.nursetraverse.testutil.PatientBuilder;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


public class AddCommandTest {

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPatientAdded modelStub = new ModelStubAcceptingPatientAdded();
        Patient validPatient = new PatientBuilder().build();

        CommandResult commandResult = new AddCommand(validPatient).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPatient), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPatient), modelStub.patientsAdded);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        AddCommand addCommand = new AddCommand(validPatient);
        ModelStub modelStub = new ModelStubWithPatient(validPatient);

        Assert.assertThrows(CommandException.class,
            AddCommand.MESSAGE_DUPLICATE_PATIENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Patient alice = new PatientBuilder().withName("Alice").build();
        Patient bob = new PatientBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different patient -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPatientBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatientBookFilePath(Path patientBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAppointmentBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatients(List<Patient> patients) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStagedPatientBook(ReadOnlyPatientBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void replaceStagedPatientBook(List<Patient> patients) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPatientBook getStagedPatientBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAnyPatientInGivenList(List<Patient> patients) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatient(Patient target, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getPatientByIndex(Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getPatientsByIndexes(Set<Index> indexes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<Patient> getStagedPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Visit> getObservableOngoingVisitList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNewOngoingVisit(Visit visit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOngoingVisit(Visit updatedVisit) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cancelOngoingVisit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unsetOngoingVisit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Visit> getOngoingVisit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean patientHasOngoingVisit(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStagedChanges() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commit(MutatorCommand command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void discardStagedChanges() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<HistoryRecord> undoTo(HistoryRecord record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HistoryRecord redo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<HistoryRecord> getHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStagedAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void replaceStagedAppointmentBook(List<Appointment> appointments) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAppointmentBook getStagedAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClashingAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecurringAppointment(Appointment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointments(Patient target, Index targetIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointment(Appointment target, Appointment editedAppointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointments(Patient patientToEdit, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getStagedAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public FilteredList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithPatient extends ModelStub {
        private final Patient patient;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return this.patient.isSamePatient(patient);
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private class ModelStubAcceptingPatientAdded extends ModelStub {
        final ArrayList<Patient> patientsAdded = new ArrayList<>();

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return patientsAdded.stream().anyMatch(patient::isSamePatient);
        }

        @Override
        public void addPatient(Patient patient) {
            requireNonNull(patient);
            patientsAdded.add(patient);
        }

        @Override
        public ReadOnlyPatientBook getStagedPatientBook() {
            return new PatientBook();
        }
    }

}
