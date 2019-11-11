package seedu.planner.logic.events.clear;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.system.UndoClearCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
//@@author OneArmyj

public class ClearEventTest {

    @Test
    public void checkClearCommand() {
        Model model = new ModelManager();
        ClearCommand clearCommand = new ClearCommand(false);
        clearCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        assertTrue(event instanceof ClearEvent);
    }

    @Test
    public void checkUndoCommandReturnType() {
        Model model = new ModelManager();
        ClearCommand clearCommand = new ClearCommand(false);
        clearCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command undoCommand = event.undo();
        assertTrue(undoCommand instanceof UndoClearCommand);
    }

    @Test
    public void checkRedoCommandReturnType() {
        Model model = new ModelManager();
        ClearCommand clearCommand = new ClearCommand(false);
        clearCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command redoCommand = event.redo();
        assertTrue(redoCommand instanceof ClearCommand);
    }

}
