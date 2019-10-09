package seedu.mark.logic.commands;

import static seedu.mark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.mark.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.commands.results.HelpCommandResult;
import seedu.mark.model.Model;
import seedu.mark.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new HelpCommandResult(SHOWING_HELP_MESSAGE);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
