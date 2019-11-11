package seedu.system.logic.commands.outofsession;

import static seedu.system.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.system.logic.commands.outofsession.OutOfSessionHelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.CommandResult;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;

public class OutOfSessionHelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult =
            new CommandResult(SHOWING_HELP_MESSAGE, true, false, OutOfSessionHelpCommand.COMMAND_TYPE);
        assertCommandSuccess(new OutOfSessionHelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
