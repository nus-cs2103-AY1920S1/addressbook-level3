package seedu.planner.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.RedoCommand;
import seedu.planner.logic.commands.UndoCommand;
import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.addcommand.AddActivityCommand;
import seedu.planner.logic.commands.deletecommand.DeleteAccommodationCommand;
import seedu.planner.logic.commands.deletecommand.DeleteActivityCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.testutil.accommodation.TypicalAccommodations;
import seedu.planner.testutil.activity.TypicalActivity;
//@@author OneArmyj
public class CommandHistoryTest {

    @Test
    public void userExecuteUndoable_addToUndoEventStack() throws CommandException {
        CommandHistory.clearUndoStack();
        Model model = new ModelManager();
        AddActivityCommand undoableCommand1 = new AddActivityCommand(TypicalActivity.ACTIVITY_ONE, false);
        DeleteActivityCommand undoableCommand2 = new DeleteActivityCommand(Index.fromOneBased(1), false);
        AddAccommodationCommand undoableCommand3 = new AddAccommodationCommand(TypicalAccommodations.ALICE, false);
        DeleteAccommodationCommand undoableCommand4 = new DeleteAccommodationCommand(Index.fromOneBased(1), false);
        undoableCommand1.execute(model);
        undoableCommand2.execute(model);
        undoableCommand3.execute(model);
        undoableCommand4.execute(model);

        assertFalse(CommandHistory.isEmptyUndoStack());

        for (int i = 0; i < 4; i++) {
            CommandHistory.getUndoEvent();
        }
        assertTrue(CommandHistory.isEmptyUndoStack());
    }

    @Test
    public void userExecuteUndo_addToRedoEventStack() throws CommandException {
        CommandHistory.clearUndoStack();
        Model model = new ModelManager();
        AddActivityCommand undoableCommand1 = new AddActivityCommand(TypicalActivity.ACTIVITY_ONE, false);
        undoableCommand1.execute(model);
        assertFalse(CommandHistory.isEmptyUndoStack());

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);
        assertFalse(CommandHistory.isEmptyRedoStack());
    }

    @Test
    public void undoableCommandAfterUndo_clearRedoStack() throws CommandException {
        CommandHistory.clearUndoStack();
        Model model = new ModelManager();
        AddActivityCommand undoableCommand1 = new AddActivityCommand(TypicalActivity.ACTIVITY_ONE, false);
        DeleteActivityCommand undoableCommand2 = new DeleteActivityCommand(Index.fromOneBased(1), false);
        AddAccommodationCommand undoableCommand3 = new AddAccommodationCommand(TypicalAccommodations.ALICE, false);
        undoableCommand1.execute(model);
        undoableCommand2.execute(model);
        assertFalse(CommandHistory.isEmptyUndoStack());

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);
        assertFalse(CommandHistory.isEmptyUndoStack());

        assertFalse(CommandHistory.isEmptyRedoStack());

        undoableCommand3.execute(model);
        assertTrue(CommandHistory.isEmptyRedoStack());
        assertFalse(CommandHistory.isEmptyUndoStack());
    }

    @Test
    public void redoCommandExecute_addToUndoStack() throws CommandException {
        CommandHistory.clearUndoStack();
        Model model = new ModelManager();
        AddActivityCommand undoableCommand1 = new AddActivityCommand(TypicalActivity.ACTIVITY_ONE, false);
        undoableCommand1.execute(model);
        assertFalse(CommandHistory.isEmptyUndoStack());

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);
        assertTrue(CommandHistory.isEmptyUndoStack());

        RedoCommand redoCommand = new RedoCommand();
        redoCommand.execute(model);
        assertFalse(CommandHistory.isEmptyUndoStack());
    }
}
