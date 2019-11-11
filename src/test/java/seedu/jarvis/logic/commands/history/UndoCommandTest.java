package seedu.jarvis.logic.commands.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.testutil.CommandStub;
import seedu.jarvis.testutil.CommandStubInverseExecutionThrowsCommandException;

/**
 * Tests the behaviour of {@code UndoCommand}.
 */
public class UndoCommandTest {
    private UndoCommand undoCommand;
    private Model model;

    /**
     * Initializes {@code undoCommand} field as a new {@code UndoCommand}.
     * Initializes {@code model} as a new {@code ModelManager}.
     */
    @BeforeEach
    public void setUp() {
        undoCommand = new UndoCommand();
        model = new ModelManager();
    }

    /**
     * Verifies that checking {@code UndoCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        assertFalse(undoCommand::hasInverseExecution);
    }

    /**
     * Verifies that if there are no available commands to be undone, a {@code CommandException} is thrown.
     */
    @Test
    public void execute_noAvailableCommands_throwsCommandException() {
        assertThrows(CommandException.class, UndoCommand.MESSAGE_NOTHING_TO_UNDO, () -> undoCommand.execute(model));
    }

    /**
     * Verifies that if the {@code UndoCommand} is supposed to undo more commands than available in the {@code Model},
     * a {@code CommandException} is thrown.
     */
    @Test
    public void execute_tooFewAvailableCommands_throwsCommandException() {
        undoCommand = new UndoCommand(Integer.MAX_VALUE);
        model.rememberExecutedCommand(new CommandStub());
        assertThrows(CommandException.class, String.format(UndoCommand.MESSAGE_TOO_MANY_UNDO, model.getHistoryRange(),
                model.getAvailableNumberOfExecutedCommands()), () -> undoCommand.execute(model));
    }

    /**
     * Verifies that if there was an unsuccessful inverse execution during the execution of an undo command to undo a
     * single command, a {@code CommandException} is thrown.
     */
    @Test
    public void execute_unsuccessfulExecutionDuringSingleUndo_throwsCommandException() {
        model.rememberExecutedCommand(new CommandStubInverseExecutionThrowsCommandException());
        assertThrows(CommandException.class, String.format(UndoCommand.MESSAGE_UNABLE_TO_UNDO, 1, 1), () ->
                undoCommand.execute(model));
        assertEquals(1, model.getAvailableNumberOfExecutedCommands());
    }

    /**
     * Verifies that if there was an unsuccessful inverse execution during the execution of an undo command to undo
     * multiple commands, a {@code CommandException} is thrown.
     */
    @Test
    public void execute_intermediateUnsuccessfulExecutionDuringMultipleUndo_throwsCommandException() {
        int numberOfCommandsToUndo = HistoryManager.getHistoryRange();
        int badCommandIndex = HistoryManager.getHistoryRange() / 2;
        undoCommand = new UndoCommand(numberOfCommandsToUndo);
        IntStream.range(0, numberOfCommandsToUndo).forEach(index -> {
            CommandStub commandStub = index > badCommandIndex
                    ? new CommandStub()
                    : new CommandStubInverseExecutionThrowsCommandException();
            model.rememberExecutedCommand(commandStub);
        });
        assertThrows(CommandException.class, String.format(UndoCommand.MESSAGE_UNABLE_TO_UNDO, numberOfCommandsToUndo,
                badCommandIndex), () -> undoCommand.execute(model));
        assertEquals(numberOfCommandsToUndo, model.getAvailableNumberOfExecutedCommands());
    }

    /**
     * Verifies that calling inverse execution of {@code UndoCommand} will always throw a {@code CommandException} with
     * the correct message.
     */
    @Test
    public void executeInverse_throwsCommandException() {
        assertThrows(CommandException.class, UndoCommand.MESSAGE_NO_INVERSE, () -> undoCommand.executeInverse(model));
    }

}
