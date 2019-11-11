package cs.f10.t1.nursetraverse.logic.commands.visit;

import static cs.f10.t1.nursetraverse.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandFailure;
import static cs.f10.t1.nursetraverse.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static cs.f10.t1.nursetraverse.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static cs.f10.t1.nursetraverse.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.getTypicalPatientBook;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.VisitTaskUtil;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Remark;
import cs.f10.t1.nursetraverse.model.visit.Visit;

/**
 * Contains integration tests (interaction with the Model, Begin, Cancel, Finish command, Parser) and unit tests for
 * {@code BeginVisitCommand}.
 */
public class BeginVisitCommandTest {

    private Model model = new ModelManager(getTypicalPatientBook(), new UserPrefs(), getTypicalAppointmentBook());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        BeginVisitCommand command = new BeginVisitCommand(outOfBoundIndex);

        assertCommandFailure(command, model, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_valid_success() {
        BeginVisitCommand command = new BeginVisitCommand(INDEX_FIRST_PATIENT);

        Model expectedModel = new ModelManager(model.getStagedPatientBook(), new UserPrefs(),
                                               model.getStagedAppointmentBook());
        Patient expectedPatient = expectedModel.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());

        try {
            CommandResult result = command.execute(model);

            //Need to test it this way because it uses the new Date(). The time must be exactly the same.
            StartDateTime now = model.getOngoingVisit().get().getStartDateTime();
            Visit visit = new Visit(
                    new Remark(""),
                    now,
                    EndDateTime.UNFINISHED_VISIT_END_DATE_TIME,
                    VisitTaskUtil.listFromPatient(expectedPatient), expectedPatient);

            expectedPatient.addVisit(visit);
            expectedModel.setNewOngoingVisit(visit);
            String expectedMessage = String.format(BeginVisitCommand.MESSAGE_START_VISIT_SUCCESS, expectedPatient);

            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(expectedModel, model);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_ongoingVisitExists_fail() {
        //Ensure repeats fail
        BeginVisitCommand command = new BeginVisitCommand(INDEX_FIRST_PATIENT);
        assertDoesNotThrow(() -> command.execute(model));
        assertCommandFailure(command, model, BeginVisitCommand.MESSAGE_START_VISIT_FAILURE);
        BeginVisitCommand commandOnAnother = new BeginVisitCommand(INDEX_SECOND_PATIENT);
        assertCommandFailure(commandOnAnother, model, BeginVisitCommand.MESSAGE_START_VISIT_FAILURE);
    }

    @Test
    public void execute_ongoingVisitConstraints_succeedAndFailAccordingly() {
        //Verify that repeat after cancelling / finishing succeed
        BeginVisitCommand beginCommand = new BeginVisitCommand(INDEX_FIRST_PATIENT);
        CancelOngoingVisitCommand cancelCommand = new CancelOngoingVisitCommand();
        FinishOngoingVisitCommand finishCommand = new FinishOngoingVisitCommand();
        assertCommandFailure(cancelCommand, model, CancelOngoingVisitCommand.MESSAGE_NO_ONGOING_VISIT);
        assertCommandFailure(finishCommand, model, FinishOngoingVisitCommand.MESSAGE_NO_ONGOING_VISIT);
        assertDoesNotThrow(() -> beginCommand.execute(model));
        assertDoesNotThrow(() -> cancelCommand.execute(model));
        assertCommandFailure(cancelCommand, model, CancelOngoingVisitCommand.MESSAGE_NO_ONGOING_VISIT);
        assertCommandFailure(finishCommand, model, FinishOngoingVisitCommand.MESSAGE_NO_ONGOING_VISIT);
        assertDoesNotThrow(() -> beginCommand.execute(model));
        assertDoesNotThrow(() -> finishCommand.execute(model));
        assertCommandFailure(cancelCommand, model, CancelOngoingVisitCommand.MESSAGE_NO_ONGOING_VISIT);
        assertCommandFailure(finishCommand, model, FinishOngoingVisitCommand.MESSAGE_NO_ONGOING_VISIT);
        assertDoesNotThrow(() -> beginCommand.execute(model));
    }

    @Test
    public void equals() {
        BeginVisitCommand firstCommand = new BeginVisitCommand(INDEX_FIRST_PATIENT);
        BeginVisitCommand secondCommand = new BeginVisitCommand(INDEX_SECOND_PATIENT);

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        BeginVisitCommand firstCommandCopy = new BeginVisitCommand(INDEX_FIRST_PATIENT);
        assertEquals(firstCommand, firstCommandCopy);

        // different types -> returns false
        assertNotEquals(firstCommand, 1);

        // null -> returns false
        assertNotEquals(firstCommand, null);

        // different patient -> returns false
        assertNotEquals(firstCommand, secondCommand);
    }
}
