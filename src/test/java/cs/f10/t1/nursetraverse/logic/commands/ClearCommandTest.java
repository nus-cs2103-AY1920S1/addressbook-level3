package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

public class ClearCommandTest {

    @Test
    public void execute_emptyPatientBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPatientBook_success() {
        Model model = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                                       TypicalAppointments.getTypicalAppointmentBook());
        Model expectedModel = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                                               TypicalAppointments.getTypicalAppointmentBook());
        expectedModel.setStagedPatientBook(new PatientBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
