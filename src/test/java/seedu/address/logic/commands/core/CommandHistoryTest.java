package seedu.address.logic.commands.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    void execute_performUndo_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertFalse(history.canUndo());

        history.addToCommandHistory(new UndoableCommandStub("cmd 1"));
        assertTrue(history.canUndo());

        history.addToCommandHistory(new UndoableCommandStub("cmd 2"));
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
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertFalse(history.canRedo());

        history.addToCommandHistory(new UndoableCommandStub("cmd 1"));
        assertFalse(history.canRedo());

        history.addToCommandHistory(new UndoableCommandStub("cmd 2"));
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

            history.addToCommandHistory(new UndoableCommandStub("cmd 3"));
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

