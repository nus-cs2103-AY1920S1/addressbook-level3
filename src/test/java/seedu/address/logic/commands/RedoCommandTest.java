package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RedoCommand.MESSAGE_EMPTY_REDO_HISTORY;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_UPDATE_BODY_DESCRIPTOR;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_UPDATE_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.Body;

//@@author ambervoong
/**
 * Contains integration tests (interaction with the Model, UndoCommand, and UndoableCommands).
 */
class RedoCommandTest {

    @Test
    public void execute_redoUpdateCommand_success() throws CommandException {
        Body body = TYPICAL_BODY;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(body);
        UpdateBodyDescriptor descriptor = TYPICAL_UPDATE_BODY_DESCRIPTOR;
        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        updateCommand.execute(model);
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        RedoCommand redoCommand = new RedoCommand();
        String expectedMessage = String.format(MESSAGE_UPDATE_ENTITY_SUCCESS, body);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addEntity(body);

        assertCommandSuccess(redoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoExecutedCommand_failure() throws CommandException {
        Body body = TYPICAL_BODY;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(body);
        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        // The command was executed but hasn't been undone yet
        model.addExecutedCommand(updateCommand);

        RedoCommand redoCommand = new RedoCommand();
        // Even though the UpdateCommand got added to history, it will not be redone unless it had been undone before.
        assertCommandFailure(redoCommand, model, MESSAGE_EMPTY_REDO_HISTORY);
    }

    @Test
    public void execute_nothingToUndo_failure() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        RedoCommand redoCommand = new RedoCommand();
        String expectedMessage = MESSAGE_EMPTY_REDO_HISTORY;
        assertCommandFailure(redoCommand, model, expectedMessage);
    }

}
