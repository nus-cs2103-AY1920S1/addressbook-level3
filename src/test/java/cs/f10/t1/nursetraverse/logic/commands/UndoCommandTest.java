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

public class UndoCommandTest {

    @Test
    public void execute_singleUndo_success() {
        Model model = new ModelManager();
        MutatorCommand commandToUndo = new DummyMutatorCommand("1");
        Model expectedModel = new ModelManager();
        String expectedMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(commandToUndo, new PatientBook(), new AppointmentBook())));

        CommandTestUtil.assertCommandSuccess(commandToUndo, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleUndo_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        MutatorCommand dummyCommand1 = new DummyMutatorCommand("1");
        MutatorCommand dummyCommand2 = new DummyMutatorCommand("2");
        MutatorCommand dummyCommand3 = new DummyMutatorCommand("3");
        String firstExpectedUndoMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(dummyCommand3, new PatientBook(), new AppointmentBook()),
                new HistoryRecord(dummyCommand2, new PatientBook(), new AppointmentBook())));
        String secondExpectedUndoMessage = UndoCommand.makeResultString(List.of(
                new HistoryRecord(dummyCommand1, new PatientBook(), new AppointmentBook())));

        CommandTestUtil.assertCommandSuccess(dummyCommand1, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);
        CommandTestUtil.assertCommandSuccess(dummyCommand2, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "2"), expectedModel);
        CommandTestUtil.assertCommandSuccess(dummyCommand3, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "3"), expectedModel);

        CommandTestUtil.assertCommandSuccess(new UndoCommand(Index.fromOneBased(2)),
                model, firstExpectedUndoMessage, expectedModel);
        CommandTestUtil.assertCommandSuccess(new UndoCommand(Index.fromOneBased(1)),
                model, secondExpectedUndoMessage, expectedModel);
    }

    @Test
    public void execute_noSuchIndex_throwsCommandException() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        MutatorCommand dummyCommand = new DummyMutatorCommand("1");
        CommandTestUtil.assertCommandSuccess(dummyCommand, model,
                String.format(DummyMutatorCommand.RESULT_PREAMBLE, "1"), expectedModel);

        CommandTestUtil.assertCommandFailure(new UndoCommand(Index.fromOneBased(2)),
                model, UndoCommand.MESSAGE_NO_SUCH_INDEX);
        CommandTestUtil.assertCommandFailure(new UndoCommand(Index.fromZeroBased(Integer.MAX_VALUE)),
                model, UndoCommand.MESSAGE_NO_SUCH_INDEX);
    }

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
