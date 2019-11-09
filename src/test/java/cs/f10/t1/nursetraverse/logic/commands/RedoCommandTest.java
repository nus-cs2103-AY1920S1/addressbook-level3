//@@author gabrielchao

package cs.f10.t1.nursetraverse.logic.commands;

import java.util.List;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.testutil.DummyMutatorCommand;

public class RedoCommandTest {

    @Test
    public void execute_redoSingleUndo_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        MutatorCommand commandToUndo = new DummyMutatorCommand("1");
        String expectedUndoMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(commandToUndo, new PatientBook(), new AppointmentBook())));
        String expectedRedoMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS,
                new HistoryRecord(commandToUndo, new PatientBook(), new AppointmentBook()).toString());

        CommandTestUtil.assertCommandSuccess(commandToUndo, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedUndoMessage, expectedModel);

        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, expectedRedoMessage, expectedModel);
    }

    @Test
    public void execute_redoMultipleUndo_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        MutatorCommand dummyCommand1 = new DummyMutatorCommand("1");
        MutatorCommand dummyCommand2 = new DummyMutatorCommand("2");
        MutatorCommand dummyCommand3 = new DummyMutatorCommand("3");
        String expectedUndoMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(dummyCommand3, new PatientBook(), new AppointmentBook()),
                new HistoryRecord(dummyCommand2, new PatientBook(), new AppointmentBook())));
        String firstExpectedRedoMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS,
                new HistoryRecord(dummyCommand2, new PatientBook(), new AppointmentBook()).toString());
        String secondExpectedRedoMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS,
                new HistoryRecord(dummyCommand3, new PatientBook(), new AppointmentBook()).toString());

        CommandTestUtil.assertCommandSuccess(dummyCommand1, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(dummyCommand2, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "2"), expectedModel);
        CommandTestUtil.assertCommandSuccess(dummyCommand3, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "3"), expectedModel);

        CommandTestUtil.assertCommandSuccess(new UndoCommand(Index.fromOneBased(2)),
                model, expectedUndoMessage, expectedModel);

        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, firstExpectedRedoMessage, expectedModel);
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, secondExpectedRedoMessage, expectedModel);
    }

    @Test
    public void execute_noHistory_throwsCommandException() {
        // Test on fresh no history model
        Model model = new ModelManager();

        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_MORE_HISTORY);

        // Test on no history model with commands undone before
        model = new ModelManager();
        Model expectedModel = new ModelManager();

        MutatorCommand commandToUndo = new DummyMutatorCommand("1");
        String expectedUndoMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(commandToUndo, new PatientBook(), new AppointmentBook())));
        String expectedRedoMessage = String.format(RedoCommand.MESSAGE_REDO_SUCCESS,
                new HistoryRecord(commandToUndo, new PatientBook(), new AppointmentBook()).toString());

        CommandTestUtil.assertCommandSuccess(commandToUndo, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedUndoMessage, expectedModel);
        CommandTestUtil.assertCommandSuccess(new RedoCommand(), model, expectedRedoMessage, expectedModel);
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_MORE_HISTORY);
    }
}
