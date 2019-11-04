package seedu.address.logic.cap.commands;

import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.cap.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;

/**
 * Encapsulates a test for the Exit command.
 */
public class ExitCommandTest {
    private Model model = new ModelCapManager();
    private Model expectedModel = new ModelCapManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
