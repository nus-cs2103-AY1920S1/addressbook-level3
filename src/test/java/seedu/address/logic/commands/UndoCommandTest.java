package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_EMPTY_UNDO_HISTORY;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNDO_FAILURE;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_UPDATE_BODY_DESCRIPTOR;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_UPDATE_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.Body;

//@@author ambervoong
/**
 * Contains integration tests (interaction with the Model and UndoableCommands).
 */
class UndoCommandTest {

    @Test
    public void execute_undoUpdateCommand_success() throws CommandException {
        Body body = TYPICAL_BODY;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(body);
        UpdateBodyDescriptor descriptor = TYPICAL_UPDATE_BODY_DESCRIPTOR;
        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        updateCommand.execute(model);

        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = String.format(MESSAGE_UNDO_SUCCESS, body.getIdNum());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEntity(model.getFilteredBodyList().get(0), body);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_undoNotExecutedCommand_failure() throws CommandException {
        Body body = TYPICAL_BODY;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(body);
        UpdateBodyDescriptor descriptor = TYPICAL_UPDATE_BODY_DESCRIPTOR;

        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        model.addExecutedCommand(updateCommand);

        UndoCommand undoCommand = new UndoCommand();
        // Even though the UpdateCommand got added to history, it will not be undone unless it had been executed.
        assertCommandFailure(undoCommand, model, MESSAGE_UNDO_FAILURE);
    }

    @Test
    public void execute_nothingToUndo_failure() throws CommandException {
        UndoCommand undoCommand = new UndoCommand();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedMessage = MESSAGE_EMPTY_UNDO_HISTORY;
        assertCommandFailure(undoCommand, model, expectedMessage);
    }

}
