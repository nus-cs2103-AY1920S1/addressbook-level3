package seedu.planner.logic.events.add;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.addcommand.AddDayCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.system.DeleteDaysCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
//@@author OneArmyj

public class AddDayEventTest {

    @Test
    public void checkAddDayCommand() throws CommandException {
        Model model = new ModelManager();
        AddDayCommand addCommand = new AddDayCommand(5, false);
        addCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        assertTrue(event instanceof AddDayEvent);
    }

    @Test
    public void checkUndoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddDayCommand addCommand = new AddDayCommand(5, false);
        addCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command undoCommand = event.undo();
        assertTrue(undoCommand instanceof DeleteDaysCommand);
    }

    @Test
    public void checkRedoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddDayCommand addCommand = new AddDayCommand(5, false);
        addCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command redoCommand = event.redo();
        assertTrue(redoCommand instanceof AddDayCommand);
    }

}
