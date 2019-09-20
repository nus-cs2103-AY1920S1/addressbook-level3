package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.core.CommandHistory;
import seedu.address.logic.commands.core.UndoableCommandStub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/*
 * Contains integration tests (interaction with the Model and CommandHistory) and unit tests for UndoCommand.
 */
class UndoCommandTest {

    @Test
    public void execute_performUndo_success() {

        CommandHistory history = new CommandHistory();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        UndoCommand undoCommand = new UndoCommand(history);
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_NO_UNDO_HISTORY_ERROR);

        String commandResultMessage = "cmd 1";
        history.addToCommandHistory(new UndoableCommandStub(commandResultMessage));

        assertCommandSuccess(undoCommand, model, commandResultMessage, expectedModel);
    }

    @Test
    public void equals() {
        CommandHistory history = new CommandHistory();
        UndoCommand undoFirstCommand = new UndoCommand(history);
        UndoCommand undoSecondCommand = new UndoCommand(history);

        // same object -> returns true
        assertTrue(undoFirstCommand.equals(undoFirstCommand));

        // same values -> returns true
        assertTrue(undoFirstCommand.equals(undoSecondCommand));

        // different types -> returns false
        assertFalse(undoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(undoFirstCommand.equals(null));
    }
}
