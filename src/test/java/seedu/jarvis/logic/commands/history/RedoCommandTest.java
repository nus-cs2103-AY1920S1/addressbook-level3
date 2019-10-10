package seedu.jarvis.logic.commands.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.HistoryManager;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;

/**
 * Tests the behaviour of {@code RedoCommand}.
 */
public class RedoCommandTest {
    private RedoCommand redoCommand;
    private Model model;

    /**
     * Initializes {@code redoCommand} field as a new {@code RedoCommand}.
     * Initializes {@code model} as a new {@code ModelManager} object.
     */
    @BeforeEach
    public void setUp() {
        redoCommand = new RedoCommand();
        model = new ModelManager();
    }

    /**
     * Verifies that checking {@code RedoCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        assertFalse(redoCommand.hasInverseExecution());
    }

    /**
     * Verifies that if there are no available commands to be redone, a {@code CommandException} is thrown.
     */
    @Test
    public void execute_noAvailableCommands_throwsCommandException() {
        assertThrows(CommandException.class, RedoCommand.MESSAGE_NOTHING_TO_REDO, () -> redoCommand.execute(model));
    }

    /**
     * Verifies that if the {@code RedoCommand} is supposed to redo more commands than available in the {@code Model},
     * a {@code CommandException} is thrown.
     */
    @Test
    public void execute_tooFewAvailableCommands_throwsCommandException() {
        redoCommand = new RedoCommand(Integer.MAX_VALUE);
        model.rememberExecutedCommand(new CommandStub());
        model.rollback();
        assertThrows(CommandException.class, String.format(RedoCommand.MESSAGE_TOO_MANY_REDO,
                model.getAvailableNumberOfInverselyExecutedCommands()), () -> redoCommand.execute(model));
    }

    /**
     * Verifies that if there was an unsuccessful execution during the execution of an redo command to redo a single
     * command, a {@code CommandException} is thrown.
     */
    @Test
    public void execute_unsuccessfulExecutionDuringSingleRedo_throwsCommandException() {
        model.rememberExecutedCommand(new CommandStubExecutionThrowsCommandException());
        model.rollback();
        System.out.println(model.getAvailableNumberOfInverselyExecutedCommands());
        assertThrows(CommandException.class, String.format(RedoCommand.MESSAGE_UNABLE_TO_REDO, 1, 1), () ->
                redoCommand.execute(model));
        assertEquals(1, model.getAvailableNumberOfInverselyExecutedCommands());
    }

    /**
     * Verifies that if there was an unsuccessful execution during the execution of an redo command to redo multiple
     * commands, a {@code CommandException} is thrown.
     */
    @Test
    public void execute_intermediateUnsuccessfulExecutionDuringMultipleRedo_throwsCommandException() {
        int numberOfCommandsToRedo = HistoryManager.getHistoryRange();
        int badCommandIndex = HistoryManager.getHistoryRange() / 2;
        redoCommand = new RedoCommand(numberOfCommandsToRedo);
        IntStream.range(0, numberOfCommandsToRedo).forEach(index -> {
            CommandStub commandStub = index < (badCommandIndex - 1)
                    ? new CommandStub()
                    : new CommandStubExecutionThrowsCommandException();
            model.rememberExecutedCommand(commandStub);
        });
        IntStream.range(0, numberOfCommandsToRedo).forEach(index -> model.rollback());
        assertThrows(CommandException.class, String.format(RedoCommand.MESSAGE_UNABLE_TO_REDO, numberOfCommandsToRedo,
                badCommandIndex), () -> redoCommand.execute(model));
    }

    /**
     * Verifies that calling inverse execution of {@code RedoCommand} will always throw a {@code CommandException} with
     * the correct message.
     */
    @Test
    public void executeInverse_throwsCommandException() {
        assertThrows(CommandException.class, RedoCommand.MESSAGE_NO_INVERSE, () -> redoCommand.executeInverse(model));
    }

    /**
     * {@code CommandStub} to be added to {@code Model}.
     * {@code CommandStub} returns true when checked for having an inverse execution.
     * {@code CommandStub} returns null and does nothing to {@code Model} when {@code CommandStub#execute(Model)} or
     * {@code CommandStub#executeInverse(Model)} is called.
     */
    private static class CommandStub extends Command {
        @Override
        public boolean hasInverseExecution() {
            return true;
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            return null;
        }

        @Override
        public CommandResult executeInverse(Model model) throws CommandException {
            return null;
        }
    }

    /**
     * {@code CommandStubExecutionThrowsCommandException} is a stub class for {@code Command} that will always throw a
     * {@code CommandException} when executed.
     * {@code CommandStubExecutionThrowsCommandException} returns true when checked for having an inverse execution.
     */
    private static class CommandStubExecutionThrowsCommandException extends CommandStub {
        @Override
        public CommandResult execute(Model model) throws CommandException {
            throw new CommandException("CommandException always thrown.");
        }
    }

}
