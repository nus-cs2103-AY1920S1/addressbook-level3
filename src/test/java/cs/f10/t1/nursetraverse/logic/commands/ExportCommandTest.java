package cs.f10.t1.nursetraverse.logic.commands;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.Messages;
import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.testutil.TypicalAppointments;
import cs.f10.t1.nursetraverse.testutil.TypicalIndexes;
import cs.f10.t1.nursetraverse.testutil.TypicalPatients;

/**
 * Tests for the ExportCommand Class.
 * Success cases will not be tested here as they rely on
 * static methods from {@Link CsvUtil}, which
 * are tested in {@Link CsvUtilTest}.
 */

public class ExportCommandTest {

    private static final String testFilename = "test";
    private static final Optional<Set<Index>> emptyIndexSet = Optional.empty();

    private Model model = new ModelManager(TypicalPatients.getTypicalPatientBook(), new UserPrefs(),
                                           TypicalAppointments.getTypicalAppointmentBook());
    private Model emptyModel = new ModelManager(new PatientBook(), new UserPrefs(), new AppointmentBook());

    @Test
    public void execute_emptyPatientBook_failure() {
        CommandTestUtil.assertCommandFailure(
                new ExportCommand(testFilename, emptyIndexSet), emptyModel, ExportCommand.MESSAGE_EMPTY);
    }

    @Test
    public void execute_outOfBoundsIndex_failure() {
        Set<Index> indexSetOutOfBounds = TypicalIndexes.getTypicalIndexSet();
        indexSetOutOfBounds.add(CommandTestUtil.OUT_OF_BOUNDS_INDEX);

        CommandTestUtil.assertCommandFailure(
                new ExportCommand(testFilename, Optional.of(indexSetOutOfBounds)),
                model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

}
