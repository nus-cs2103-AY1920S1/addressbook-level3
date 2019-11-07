package unrealunity.visit.logic.commands;

import static unrealunity.visit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unrealunity.visit.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import unrealunity.visit.model.Model;
import unrealunity.visit.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT,
                false, false, false, false, false, true, false);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
