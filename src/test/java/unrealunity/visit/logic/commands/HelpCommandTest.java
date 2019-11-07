package unrealunity.visit.logic.commands;

import static unrealunity.visit.logic.commands.CommandTestUtil.assertCommandSuccess;
import static unrealunity.visit.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import unrealunity.visit.model.Model;
import unrealunity.visit.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE,
                true, false, false, false, false, false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
