package cs.f10.t1.nursetraverse.logic.commands;

import java.util.List;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.testutil.DummyMutatorCommand;

public class UndoCommandTest {

    @Test
    public void execute_noArgsNoHistory_throwsCommandException() {
        // Test on fresh no history model
        Model model = new ModelManager();

        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_MORE_HISTORY);

        // Test on no history model with commands undone before
        MutatorCommand commandToUndo = new DummyMutatorCommand("1");
        Model expectedModel = new ModelManager();
        String expectedMessage = UndoCommand.makeResultString(List.of(
                        new HistoryRecord(commandToUndo, new PatientBook(), new AppointmentBook())));

        CommandTestUtil.assertCommandSuccess(commandToUndo, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);

        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_MORE_HISTORY);
    }
}
