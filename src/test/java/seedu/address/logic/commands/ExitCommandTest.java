package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_EXIT_FROM_SERVE_MODE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalBorrowers.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult =
                CommandResult.commandResultExit(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_exitInServeMode_failure() {
        model.registerBorrower(ALICE);
        model.setServingBorrower(ALICE);
        Command command = new ExitCommand();
        assertCommandFailure(command, model, MESSAGE_CANNOT_EXIT_FROM_SERVE_MODE);
    }
}
