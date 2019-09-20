package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.core.CommandHistory;
import seedu.address.logic.commands.core.CommandResult;
import seedu.address.logic.commands.core.UndoableCommandStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model and CommandHistory) and unit tests for RedoCommand.
 */
class RedoCommandTest {

    @Test
    public void execute_performRedo_success() {

        CommandHistory history = new CommandHistory();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        RedoCommand redoCommand = new RedoCommand(history);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_NO_REDO_HISTORY_ERROR);

        String commandResultMessage = "cmd 1";
        history.addToCommandHistory(new UndoableCommandStub(commandResultMessage));
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_NO_REDO_HISTORY_ERROR);

        try {
            Assertions.assertTrue(history.performUndo(model).equals(new CommandResult(commandResultMessage)));
            assertCommandSuccess(redoCommand, model, commandResultMessage, expectedModel);

        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_NO_REDO_HISTORY_ERROR);
    }

    @Test
    public void equals() {
        CommandHistory history = new CommandHistory();
        RedoCommand redoFirstCommand = new RedoCommand(history);
        RedoCommand redoSecondCommand = new RedoCommand(history);

        // same object -> returns true
        Assertions.assertTrue(redoFirstCommand.equals(redoFirstCommand));

        // same values -> returns true
        Assertions.assertTrue(redoFirstCommand.equals(redoSecondCommand));

        // different types -> returns false
        Assertions.assertFalse(redoFirstCommand.equals(1));

        // null -> returns false
        Assertions.assertFalse(redoFirstCommand.equals(null));
    }
}
