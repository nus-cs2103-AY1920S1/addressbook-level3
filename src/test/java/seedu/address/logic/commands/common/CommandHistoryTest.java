package seedu.address.logic.commands.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.utils.ReversibleCommandStub;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TestUtil;

class CommandHistoryTest {
    private CommandHistory history;
    private ModelManager model;
    private ModelManager expectedModel;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
        model = TestUtil.getTypicalModelManager();
        expectedModel = TestUtil.getTypicalModelManager();
    }

    @Test
    void execute_performUndo_success() {
        assertFalse(history.canUndo());

        history.addToCommandHistory(new ReversibleCommandStub("cmd 1"));
        assertTrue(history.canUndo());

        history.addToCommandHistory(new ReversibleCommandStub("cmd 2"));
        assertTrue(history.canUndo());

        try {
            assertTrue(history.performUndo(model).equals(new CommandResult("cmd 2")));
            assertTrue(history.canUndo());

            assertTrue(history.performUndo(model).equals(new CommandResult("cmd 1")));
            assertFalse(history.canUndo());

        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        assertThrows(CommandException.class,
                CommandHistory.MESSAGE_NO_UNDO_HISTORY_ERROR, () -> history.performUndo(model));

        assertTrue(model.equals(expectedModel));
    }

    @Test
    void canRedo() {
        assertFalse(history.canRedo());

        history.addToCommandHistory(new ReversibleCommandStub("cmd 1"));
        assertFalse(history.canRedo());

        history.addToCommandHistory(new ReversibleCommandStub("cmd 2"));
        assertFalse(history.canRedo());

        try {
            assertTrue(history.performUndo(model).equals(new CommandResult("cmd 2")));
            assertTrue(history.canUndo());
            assertTrue(history.canRedo());

            assertTrue(history.performUndo(model).equals(new CommandResult("cmd 1")));
            assertFalse(history.canUndo());
            assertTrue(history.canRedo());

            assertTrue(history.performRedo(model).equals(new CommandResult("cmd 1")));
            assertTrue(history.canUndo());
            assertTrue(history.canRedo());

            history.addToCommandHistory(new ReversibleCommandStub("cmd 3"));
            assertTrue(history.canUndo());
            assertFalse(history.canRedo());

        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

        assertThrows(CommandException.class,
                CommandHistory.MESSAGE_NO_REDO_HISTORY_ERROR, () -> history.performRedo(model));

        assertTrue(model.equals(expectedModel));
    }
}

