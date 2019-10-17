package tagline.logic.commands;

import static tagline.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tagline.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.CommandResult.ViewType;
import tagline.model.Model;
import tagline.model.ModelManager;

public class ExitCommandTest {

    private static final ViewType EXIT_COMMAND_VIEW_TYPE = ViewType.NONE;
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT,
                false, true, EXIT_COMMAND_VIEW_TYPE);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
