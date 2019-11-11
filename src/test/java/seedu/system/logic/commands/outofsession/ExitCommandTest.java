package seedu.system.logic.commands.outofsession;

import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.logic.commands.outofsession.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.CommandResult;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult =
            new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, ExitCommand.COMMAND_TYPE);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
