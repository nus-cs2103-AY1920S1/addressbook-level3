package seedu.ezwatchlist.logic.commands;

import static seedu.ezwatchlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ezwatchlist.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
