package seedu.jarvis.logic.commands;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.AddressModelManager;

public class ExitCommandTest {
    private AddressModel addressModel = new AddressModelManager();
    private AddressModel expectedAddressModel = new AddressModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), addressModel, expectedCommandResult, expectedAddressModel);
    }
}
