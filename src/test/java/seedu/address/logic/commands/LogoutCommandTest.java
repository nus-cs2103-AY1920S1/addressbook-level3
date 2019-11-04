package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.PersonBuilder;


class LogoutCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void execute_swap_success() {
        model.setSession(new PersonBuilder().build());
        expectedModel.setSession(new PersonBuilder().build());
        CommandResult expectedCommandResult = new CommandResult(LogoutCommand.MESSAGE_SUCCESS, false, false, true);
        assertCommandSuccess(new LogoutCommand(), model, expectedCommandResult, expectedModel);
    }
}
