package seedu.address.logic.cap.commands;

import static seedu.address.logic.cap.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.cap.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.Model;
import seedu.address.model.cap.ModelCapManager;

/**
 * Contains integration tests (interaction with the Model) for {@code HelpCommand}.
 */
public class HelpCommandTest {
    private Model model = new ModelCapManager();
    private Model expectedModel = new ModelCapManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
