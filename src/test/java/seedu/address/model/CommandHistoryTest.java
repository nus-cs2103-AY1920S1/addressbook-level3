package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.BOOK_2;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

public class CommandHistoryTest {
    private CommandHistory commandHistory = new CommandHistoryManager();
    // Must be a unique book, so that execute does not throw CommandException
    private Book testBook = new BookBuilder()
            .withSerialNumber("B99999")
            .withTitle("TestBook")
            .withAuthor("Tester")
            .build();
    private ReversibleCommand testCommand = new AddCommand(testBook);

    @Test
    public void commit_nullReversibleCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> commandHistory.commit(null));
    }

    @Test
    public void commit_validReversibleCommand_successfulCommit() {
        Model model = new ModelManager();
        try {
            testCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }
        commandHistory.commit(testCommand);
        assertThrows(CommandHistoryManager.NoRedoableCommandException.class, () -> commandHistory.getRedoCommand());

        Pair<Command, ReversibleCommand> commands = commandHistory.getUndoCommand();
        assertEquals(testCommand, commands.getValue());
    }

    @Test
    public void canUndo_notUndoable_returnsFalse() {
        assertFalse(commandHistory.canUndo());
    }

    @Test
    public void canUndo_undoable_returnsTrue() {
        commandHistory.commit(testCommand);
        assertTrue(commandHistory.canUndo());
    }

    @Test
    public void canRedo_notRedoable_returnsFalse() {
        assertFalse(commandHistory.canRedo());
    }

    @Test
    public void canRedo_redoable_returnsTrue() {
        Model model = new ModelManager();
        try {
            testCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }
        commandHistory.commit(testCommand);
        commandHistory.getUndoCommand();
        assertTrue(commandHistory.canRedo());
    }

    @Test
    public void getUndoCommand_notUndoable_throwsNoUndoableCommandException() {
        assertThrows(CommandHistoryManager.NoUndoableCommandException.class, () -> commandHistory.getUndoCommand());
    }

    @Test
    public void getUndoCommand_undoable_returnsUndoCommandPair() {
        Model model = new ModelManager();
        try {
            testCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }
        commandHistory.commit(testCommand);
        assertTrue(commandHistory.canUndo());

        Pair<Command, ReversibleCommand> commands = commandHistory.getUndoCommand();
        assertNotEquals(commands.getKey(), null);
        assertNotEquals(commands.getValue(), null);
        assertEquals(testCommand, commands.getValue());
        assertEquals(testCommand.getUndoCommand(), commands.getKey());
    }

    @Test
    public void getRedoCommand_notRedoable_throwsNoRedoableCommandException() {
        assertThrows(CommandHistoryManager.NoRedoableCommandException.class, () -> commandHistory.getRedoCommand());
    }

    @Test
    public void getRedoCommand_redoable_returnsRedoCommand() {
        Model model = new ModelManager();
        try {
            testCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }
        commandHistory.commit(testCommand);
        assertNotEquals(commandHistory.getUndoCommand(), null);
        assertTrue(commandHistory.canRedo());

        Command redoCommand = commandHistory.getRedoCommand();
        assertNotEquals(redoCommand, null);
        assertEquals(testCommand, redoCommand);
    }

    @Test
    public void reset() {
        CommandHistory commandHistoryManager = new CommandHistoryManager();
        commandHistory.commit(testCommand);
        assertNotEquals(commandHistory, commandHistoryManager);

        commandHistory.reset();
        assertEquals(commandHistory, commandHistoryManager);
    }

    @Test
    public void equals() {
        CommandHistory commandHistoryManager1 = new CommandHistoryManager();
        CommandHistory commandHistoryManager2 = new CommandHistoryManager();

        ReversibleCommand addBook1Command = new AddCommand(BOOK_1);
        ReversibleCommand addBook2Command = new AddCommand(BOOK_2);

        commandHistoryManager1.commit(addBook1Command);
        commandHistoryManager2.commit(addBook2Command);

        // same object -> returns true
        assertTrue(commandHistory.equals(commandHistory));

        // same values -> returns true
        CommandHistory commandHistoryManager1copy = new CommandHistoryManager();
        commandHistoryManager1copy.commit(addBook1Command);
        assertTrue(commandHistoryManager1.equals(commandHistoryManager1copy));

        // different types -> returns false
        assertFalse(commandHistoryManager1.equals(1));

        // different object -> returns false
        assertFalse(commandHistoryManager1.equals(commandHistoryManager2));
    }
}
