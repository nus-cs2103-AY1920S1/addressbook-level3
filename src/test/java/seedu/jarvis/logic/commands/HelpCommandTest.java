package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        expectedModel = new ModelManager();
    }

    /**
     * Verifies that checking HelpCommand for the availability of inverse execution returns false.
     */
    @Test
    public void test_hasInverseExecution() {
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.hasInverseExecution());
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    /**
     * Verifies that calling inverse execution of HelpCommand will always throw command exception with the correct
     * message.
     */
    @Test
    public void test_executeInverse_exceptionThrown() {
        HelpCommand helpCommand = new HelpCommand();
        assertThrows(CommandException.class,
                HelpCommand.MESSAGE_NO_INVERSE, () -> helpCommand.executeInverse(model));
    }
}
