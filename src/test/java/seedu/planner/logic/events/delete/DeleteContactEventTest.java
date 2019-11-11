package seedu.planner.logic.events.delete;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.commands.Command;
import seedu.planner.logic.commands.addcommand.AddContactCommand;
import seedu.planner.logic.commands.deletecommand.DeleteContactCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.testutil.contact.TypicalContacts;
//@@author OneArmyj

public class DeleteContactEventTest {

    @Test
    public void checkDeleteContactCommand() throws CommandException {
        Model model = new ModelManager();
        AddContactCommand addCommand = new AddContactCommand(TypicalContacts.ALICE, false);
        addCommand.execute(model);
        DeleteContactCommand deleteCommand = new DeleteContactCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        assertTrue(event instanceof DeleteContactEvent);
    }

    @Test
    public void checkUndoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddContactCommand addCommand = new AddContactCommand(TypicalContacts.ALICE, false);
        addCommand.execute(model);
        DeleteContactCommand deleteCommand = new DeleteContactCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command undoCommand = event.undo();
        assertTrue(undoCommand instanceof AddContactCommand);
    }

    @Test
    public void checkRedoCommandReturnType() throws CommandException {
        Model model = new ModelManager();
        AddContactCommand addCommand = new AddContactCommand(TypicalContacts.ALICE, false);
        addCommand.execute(model);
        DeleteContactCommand deleteCommand = new DeleteContactCommand(Index.fromOneBased(1), false);
        deleteCommand.execute(model);
        Event event = CommandHistory.getUndoEvent();
        Command redoCommand = event.redo();
        assertTrue(redoCommand instanceof DeleteContactCommand);
    }

}
