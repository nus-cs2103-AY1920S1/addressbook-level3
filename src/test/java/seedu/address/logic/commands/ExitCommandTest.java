package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBookModel;
import seedu.address.model.AddressBookModelManager;

public class ExitCommandTest {
    private AddressBookModel addressBookModel = new AddressBookModelManager();
    private AddressBookModel expectedAddressBookModel = new AddressBookModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), addressBookModel, expectedCommandResult, expectedAddressBookModel);
    }
}
