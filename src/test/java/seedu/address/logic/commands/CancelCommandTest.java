package seedu.address.logic.commands;

import static seedu.address.logic.commands.CancelCommand.SHOWING_CANCEL_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CancelCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_CANCEL_MESSAGE,
                false, false, false, false,
                false, false, false, false);

        assertCommandSuccess(new CancelCommand(), model, expectedCommandResult, expectedModel);
    }
}
