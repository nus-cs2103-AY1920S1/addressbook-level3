package seedu.planner.logic.events.delete;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.addcommand.AddActivityCommand;
import seedu.planner.logic.commands.deletecommand.DeleteActivityCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.testutil.activity.TypicalActivity;
//@@author OneArmyj

public class DeleteActivityEventTest {

    @Test
    public void checkDeleteActivityCommand() throws CommandException {
        Model model = new ModelManager();
        AddActivityCommand addCommand = new AddActivityCommand(TypicalActivity.ACTIVITY_ONE, false);
        addCommand.execute(model);
        DeleteActivityCommand deleteCommand = new DeleteActivityCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        assertTrue(event instanceof DeleteActivityEvent);
    }

    @Test
    public void checkUndoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddActivityCommand addCommand = new AddActivityCommand(TypicalActivity.ACTIVITY_ONE, false);
        addCommand.execute(model);
        DeleteActivityCommand deleteCommand = new DeleteActivityCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command undoCommand = event.undo();
        assertTrue(undoCommand instanceof AddActivityCommand);
    }

    @Test
    public void checkRedoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddActivityCommand addCommand = new AddActivityCommand(TypicalActivity.ACTIVITY_ONE, false);
        addCommand.execute(model);
        DeleteActivityCommand deleteCommand = new DeleteActivityCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command redoCommand = event.redo();
        assertTrue(redoCommand instanceof DeleteActivityCommand);
    }

}

