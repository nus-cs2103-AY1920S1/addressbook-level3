package calofit.logic.commands;

import static calofit.logic.commands.CommandTestUtil.assertCommandSuccess;

import calofit.model.Model;
import calofit.model.ModelManager;
import org.junit.jupiter.api.Test;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
