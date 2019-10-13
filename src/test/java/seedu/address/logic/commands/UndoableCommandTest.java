package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RedoCommand.MESSAGE_EMPTY_REDO_HISTORY;
import static seedu.address.logic.commands.UpdateCommand.MESSAGE_UPDATE_ENTITY_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_UPDATE_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.Body;
import seedu.address.testutil.BodyBuilder;

//@@author ambervoong
class UndoableCommandTest {

    @Test
    void test_commandStateEnum() {
        assertEquals(UndoableCommand.UndoableCommandState.UNDOABLE.toString(), "UNDOABLE");
    }

    @Test
    void getCommandState() {
        // Default command state of an UndoableCommand
        Body body = new BodyBuilder().build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(body);
        UpdateCommand command = new UpdateCommand(body.getBodyIdNum(), descriptor);
        assertEquals(UndoableCommand.UndoableCommandState.PRE_EXECUTION, command.getCommandState());
    }

    @Test
    void setUndoableGetCommandState() {
        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        updateCommand.setUndoable();
        assertEquals(updateCommand.getCommandState(), UndoableCommand.UndoableCommandState.UNDOABLE);
    }

    @Test
    void setRedoable() {
        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        updateCommand.setRedoable();
        assertEquals(updateCommand.getCommandState(), UndoableCommand.UndoableCommandState.REDOABLE);
    }

    @Test
    void execution_autoSetUndoable_true() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(TYPICAL_BODY);

        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        updateCommand.execute(model);
        // Executing a command automatically sets its state to Undoable.
        assertEquals(updateCommand.getCommandState(), UndoableCommand.UndoableCommandState.UNDOABLE);

    }

    @Test
    void redoCommand_undoneCommandExists_success() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(TYPICAL_BODY);
        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        updateCommand.execute(model);

        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(model);

        String expectedString = String.format(MESSAGE_UPDATE_ENTITY_SUCCESS, TYPICAL_BODY);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addEntity(TYPICAL_BODY);
        RedoCommand redoCommand = new RedoCommand();
        assertCommandSuccess(redoCommand, model, expectedString, expectedModel);
    }

    @Test
    void redoCommand_emptyRedoStack_failure() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(TYPICAL_BODY);
        UpdateCommand updateCommand = TYPICAL_UPDATE_COMMAND;
        updateCommand.execute(model);

        RedoCommand redoCommand = new RedoCommand();
        assertCommandFailure(redoCommand, model, MESSAGE_EMPTY_REDO_HISTORY);
    }
}
