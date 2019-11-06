package cs.f10.t1.nursetraverse.logic.commands;

import java.util.List;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.testutil.DummyMutatorCommand;

public class RedoCommandTest {

    @Test
    public void execute_success_noException() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        MutatorCommand commandToUndo = new DummyMutatorCommand("1");
        String expectedUndoMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(commandToUndo, new PatientBook())));
        String expectedRedoMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS,
                new HistoryRecord(commandToUndo, new PatientBook()).toString());

        CommandTestUtil.assertCommandSuccess(commandToUndo, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedUndoMessage, expectedModel);

        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, expectedRedoMessage, expectedModel);
    }

    @Test
    public void execute_noUndo_throwsCommandException() {
        // Test on fresh no history model
        Model model = new ModelManager();

        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_MORE_HISTORY);

        // Test on no history model with commands undone before
        model = new ModelManager();
        Model expectedModel = new ModelManager();

        MutatorCommand commandToUndo = new DummyMutatorCommand("1");
        String expectedUndoMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(commandToUndo, new PatientBook())));
        String expectedRedoMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS,
                new HistoryRecord(commandToUndo, new PatientBook()).toString());

        CommandTestUtil.assertCommandSuccess(commandToUndo, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedUndoMessage, expectedModel);
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, expectedRedoMessage, expectedModel);
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_MORE_HISTORY);
    }
}
