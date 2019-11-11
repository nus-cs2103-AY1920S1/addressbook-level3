package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandFailure;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.showPatientAtIndex;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.logic.commands.visit.BeginVisitCommand;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.exceptions.PatientHasOngoingVisitException;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalIndexes;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                                           TypicalAppointments.getTypicalAppointmentBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Patient patientToDelete = model.getFilteredPatientList().get(TypicalIndexes.INDEX_FIRST_PATIENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PATIENT_SUCCESS, patientToDelete);

        ModelManager expectedModel = new ModelManager(model.getStagedPatientBook(), new UserPrefs(),
                                                      model.getStagedAppointmentBook());
        expectedModel.deletePatient(patientToDelete);
        expectedModel.deleteAppointments(patientToDelete, TypicalIndexes.INDEX_FIRST_PATIENT);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPatientAtIndex(model, TypicalIndexes.INDEX_FIRST_PATIENT);

        Patient patientToDelete = model.getFilteredPatientList().get(TypicalIndexes.INDEX_FIRST_PATIENT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PATIENT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PATIENT_SUCCESS, patientToDelete);

        Model expectedModel = new ModelManager(model.getStagedPatientBook(), new UserPrefs(),
                                               model.getStagedAppointmentBook());
        expectedModel.deletePatient(patientToDelete);
        expectedModel.deleteAppointments(patientToDelete, TypicalIndexes.INDEX_FIRST_PATIENT);
        showNoPatient(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PATIENT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(TypicalIndexes.INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PATIENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void execute_patientHasOngoingVisit_exception() {
        BeginVisitCommand beginVisitCommand = new BeginVisitCommand(TypicalIndexes.INDEX_FIRST_PATIENT);
        assertDoesNotThrow(() -> beginVisitCommand.execute(model));
        DeleteCommand deleteCommand = new DeleteCommand(TypicalIndexes.INDEX_FIRST_PATIENT);
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_INVALID_PATIENT_HAS_ONGOING_VISIT);
        //There is also a check within the patient book
        Patient patientToDelete = model.getFilteredPatientList().get(TypicalIndexes.INDEX_FIRST_PATIENT.getZeroBased());
        assertThrows(PatientHasOngoingVisitException.class, () -> model.deletePatient(patientToDelete));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPatient(Model model) {
        model.updateFilteredPatientList(p -> false);

        assertTrue(model.getFilteredPatientList().isEmpty());
    }
}
