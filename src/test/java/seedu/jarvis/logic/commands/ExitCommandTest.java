package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;

public class ExitCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    /**
     * Verifies that checking {@code ExitCommand} for the availability of inverse execution returns false.
     */
    @Test
    public void hasInverseExecution() {
        ExitCommand exitCommand = new ExitCommand();
        assertFalse(exitCommand.hasInverseExecution());
    }

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }

    /**
     * Verifies that calling inverse execution of {@code ExitCommand} will always throw a {@code CommandException} with
     * the correct message.
     */
    @Test
    public void executeInverse_throwsCommandException() {
        ExitCommand exitCommand = new ExitCommand();
        assertThrows(CommandException.class,
                ExitCommand.MESSAGE_NO_INVERSE, () -> exitCommand.executeInverse(model));
    }
}
