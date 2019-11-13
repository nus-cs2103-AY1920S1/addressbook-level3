package seedu.planner.logic.events.delete;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.deletecommand.DeleteAccommodationCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.testutil.accommodation.TypicalAccommodations;
//@@author OneArmyj

public class DeleteAccommodationEventTest {

    @Test
    public void checkDeleteAccommodationCommand() throws CommandException {
        Model model = new ModelManager();
        AddAccommodationCommand addCommand = new AddAccommodationCommand(TypicalAccommodations.ALICE, false);
        addCommand.execute(model);
        DeleteAccommodationCommand deleteCommand = new DeleteAccommodationCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        assertTrue(event instanceof DeleteAccommodationEvent);
    }

    @Test
    public void checkUndoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddAccommodationCommand addCommand = new AddAccommodationCommand(TypicalAccommodations.ALICE, false);
        addCommand.execute(model);
        DeleteAccommodationCommand deleteCommand = new DeleteAccommodationCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command undoCommand = event.undo();
        assertTrue(undoCommand instanceof AddAccommodationCommand);
    }

    @Test
    public void checkRedoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddAccommodationCommand addCommand = new AddAccommodationCommand(TypicalAccommodations.ALICE, false);
        addCommand.execute(model);
        DeleteAccommodationCommand deleteCommand = new DeleteAccommodationCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command redoCommand = event.redo();
        assertTrue(redoCommand instanceof DeleteAccommodationCommand);
    }

}
