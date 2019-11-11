package cs.f10.t1.nursetraverse.logic.commands.visit;

import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandSuccess;
import static cs.f10.t1.nursetraverse.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static cs.f10.t1.nursetraverse.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static cs.f10.t1.nursetraverse.testutil.TypicalPatients.getTypicalPatientBook;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, Begin, Cancel, Finish command, Parser) and unit tests for
 * {@code CancelOngoingVisitCommand}. There is some overlap with BeginVisitCommandTest, so some of the tests are
 * not here.
 */
public class CancelOngoingVisitCommandTest {

    private Model model = new ModelManager(getTypicalPatientBook(), new UserPrefs(), getTypicalAppointmentBook());

    @Test
    public void execute_valid_success() {
        Model expectedModel = new ModelManager(model.getStagedPatientBook(), new UserPrefs(),
                                               model.getStagedAppointmentBook());

        BeginVisitCommand beginCommand = new BeginVisitCommand(INDEX_FIRST_PATIENT);
        CancelOngoingVisitCommand cancelCommand = new CancelOngoingVisitCommand();
        assertDoesNotThrow(() -> beginCommand.execute(model));

        assertCommandSuccess(cancelCommand, model, CancelOngoingVisitCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(model.getOngoingVisit(), Optional.empty());
    }
}
