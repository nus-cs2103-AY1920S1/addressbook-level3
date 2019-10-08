package seedu.address.logic.commands;

//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.deliverymans.logic.commands.universal.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

//import seedu.deliverymans.logic.commands.universal.HelpCommand;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.deliverymans.logic.commands.CommandResult;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        //assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
