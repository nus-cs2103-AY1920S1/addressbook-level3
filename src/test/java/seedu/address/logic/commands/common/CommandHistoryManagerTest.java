//@@author SakuraBlossom
package seedu.address.logic.commands.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.utils.ReversibleActionPairCommandStub;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TestUtil;

/**
 * Contains unit tests for CommandHistoryTest.
 */
class CommandHistoryManagerTest {
    private CommandHistoryManager history;
    private ModelManager model;
    private ModelManager expectedModel;

    @BeforeEach
    public void setUp() {
        history = new CommandHistoryManager();
        model = TestUtil.getTypicalModelManager();
        expectedModel = TestUtil.getTypicalModelManager();
    }

    @Test
    void execute_performUndo_success() {
        assertFalse(history.canUndo());

        // True: Able to add action pair to the stack. Cmd 1 added to the undo stack, but not the redo stack.
        history.addToCommandHistory(new ReversibleActionPairCommandStub("cmd 1"));
        assertTrue(history.canUndo());

        // True: Able to add  action pair to the stack. Cmd 2 added to the undo stack, but not the redo stack.
        history.addToCommandHistory(new ReversibleActionPairCommandStub("cmd 2"));
        assertTrue(history.canUndo());

        // False: throws exception when given null
        assertThrows(NullPointerException.class, () -> history.addToCommandHistory(null));

        // True: able to undo the cmd 2. undo and redo stack should not be empty.
        CommandResult performUndoResult = assertDoesNotThrow(() -> history.performUndo(model));
        assertTrue(performUndoResult.equals(new CommandResult("cmd 2")));
        assertTrue(history.canUndo());

        // True: able to undo the cmd 1. undo stack should be empty.
        performUndoResult = assertDoesNotThrow(() -> history.performUndo(model));
        assertTrue(performUndoResult.equals(new CommandResult("cmd 1")));
        assertFalse(history.canUndo());

        // False: Nothing left to undo
        assertThrows(CommandException.class,
                CommandHistoryManager.MESSAGE_NO_UNDO_HISTORY_ERROR, () -> history.performUndo(model));

        // True: model not be modified.
        assertTrue(model.equals(expectedModel));
    }

    @Test
    void execute_performRedo_success() {
        assertFalse(history.canRedo());

        // False: Able to add action pair to the stack. Cmd 1 added to the undo stack, but not the redo stack.
        history.addToCommandHistory(new ReversibleActionPairCommandStub("cmd 1"));
        assertFalse(history.canRedo());

        // False: Able to add action pair to the stack. Cmd 2 added to the undo stack, but not the redo stack.
        history.addToCommandHistory(new ReversibleActionPairCommandStub("cmd 2"));
        assertFalse(history.canRedo());

        // True: able to undo the cmd 2. undo and redo stack should not be empty.
        CommandResult performUndoResult = assertDoesNotThrow(() -> history.performUndo(model));
        assertTrue(performUndoResult.equals(new CommandResult("cmd 2")));
        assertTrue(history.canRedo());

        // True: able to undo the cmd 1. undo stack should be empty.
        performUndoResult = assertDoesNotThrow(() -> history.performUndo(model));
        assertTrue(performUndoResult.equals(new CommandResult("cmd 1")));
        assertTrue(history.canRedo());

        // True: able to redo the cmd 1. undo stack should not be empty.
        performUndoResult = assertDoesNotThrow(() -> history.performRedo(model));
        assertTrue(performUndoResult.equals(new CommandResult("cmd 1")));
        assertTrue(history.canUndo());
        assertTrue(history.canRedo());

        // True: able to redo the cmd 2. undo stack should not be empty.
        performUndoResult = assertDoesNotThrow(() -> history.performRedo(model));
        assertTrue(performUndoResult.equals(new CommandResult("cmd 2")));
        assertTrue(history.canUndo());
        assertFalse(history.canRedo());

        // exception thrown on undo request
        assertThrows(CommandException.class,
                CommandHistoryManager.MESSAGE_NO_REDO_HISTORY_ERROR, () -> history.performRedo(model));

        assertTrue(model.equals(expectedModel));
    }


    @Test
    void execute_performRedo_clearRedoStackOnAddCommand() {
        assertFalse(history.canRedo());

        // False: Able to add action pair to the stack. Cmd 1 added to the undo stack, but not the redo stack.
        history.addToCommandHistory(new ReversibleActionPairCommandStub("cmd 1"));
        assertFalse(history.canRedo());

        // True: able to undo the cmd 1. undo stack should be empty.
        CommandResult performUndoResult = assertDoesNotThrow(() -> history.performUndo(model));
        assertTrue(performUndoResult.equals(new CommandResult("cmd 1")));
        assertTrue(history.canRedo());

        // exception thrown on undo request
        assertThrows(CommandException.class,
                CommandHistoryManager.MESSAGE_NO_UNDO_HISTORY_ERROR, () -> history.performUndo(model));

        // False: Able to add action pair to the stack. Cmd 1 added to the undo stack, but redo stack is cleared.
        history.addToCommandHistory(new ReversibleActionPairCommandStub("cmd 1"));
        assertTrue(history.canUndo());
        assertFalse(history.canRedo());

        // exception thrown on redo request
        assertThrows(CommandException.class,
                CommandHistoryManager.MESSAGE_NO_REDO_HISTORY_ERROR, () -> history.performRedo(model));

        assertTrue(model.equals(expectedModel));
    }
}

