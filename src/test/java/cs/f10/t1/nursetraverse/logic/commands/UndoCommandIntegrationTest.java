package cs.f10.t1.nursetraverse.logic.commands;

import static cs.f10.t1.nursetraverse.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.testutil.EditPatientDescriptorBuilder;
import cs.f10.t1.nursetraverse.testutil.PatientBuilder;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

/**
 * Contains integration tests (interaction with other commands) for {@code UndoCommand}.
 */
public class UndoCommandIntegrationTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                TypicalAppointments.getTypicalAppointmentBook());
        expectedModel = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                TypicalAppointments.getTypicalAppointmentBook());
    }

    @Test
    public void execute_undoModifyExistingPatient_success() {
        Patient editedPatient = new PatientBuilder().build();
        //Ensure that this test case uses a Patient that doesn't have visits, because you cannot edit visits
        //and this testcase will fail if it uses a Patient with a visit
        //In this case we'll use the 4th patient, Daniel
        EditCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        EditCommand editCommand = new EditCommand(Index.fromOneBased(4), descriptor);

        model.setPatient(model.getFilteredPatientList().get(3), editedPatient);
        model.commit(editCommand);

        assertCommandSuccess(new UndoCommand(), model,
                UndoCommand.makeResultString(List.of(
                        new HistoryRecord(editCommand, TypicalPatients.getTypicalPatientBook(),
                                TypicalAppointments.getTypicalAppointmentBook()))),
                expectedModel);
    }

    @Test
    public void execute_undoAddNewPatient_success() {
        Patient validPatient = new PatientBuilder().build();
        MutatorCommand addCommand = new AddCommand(validPatient);

        model.addPatient(validPatient);
        model.commit(addCommand);

        assertCommandSuccess(new UndoCommand(), model,
                UndoCommand.makeResultString(List.of(
                        new HistoryRecord(addCommand, TypicalPatients.getTypicalPatientBook(),
                                TypicalAppointments.getTypicalAppointmentBook()))),
                expectedModel);
    }

    @Test
    public void execute_undoDeletePatient_success() {
        MutatorCommand deleteCommand = new DeleteCommand(Index.fromOneBased(4));

        model.deletePatient(model.getPatientByIndex(Index.fromOneBased(4)));
        model.commit(deleteCommand);

        assertCommandSuccess(new UndoCommand(), model,
                UndoCommand.makeResultString(List.of(
                        new HistoryRecord(deleteCommand, TypicalPatients.getTypicalPatientBook(),
                                TypicalAppointments.getTypicalAppointmentBook()))),
                expectedModel);
    }
}
