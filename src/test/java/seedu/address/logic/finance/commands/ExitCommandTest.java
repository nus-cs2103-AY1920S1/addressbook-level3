package seedu.address.logic.finance.commands;

import static seedu.address.logic.finance.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.finance.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.finance.Model;
import seedu.address.model.finance.ModelFinanceManager;

public class ExitCommandTest {
    private Model financeModel = new ModelFinanceManager();
    private Model expectedModel = new ModelFinanceManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult =
            new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, false, true);
        assertCommandSuccess(new ExitCommand(), financeModel, expectedCommandResult, expectedModel);
    }
}
