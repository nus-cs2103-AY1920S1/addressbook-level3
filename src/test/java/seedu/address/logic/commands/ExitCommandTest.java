package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.switches.SwitchToExitCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(Messages.MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new SwitchToExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
