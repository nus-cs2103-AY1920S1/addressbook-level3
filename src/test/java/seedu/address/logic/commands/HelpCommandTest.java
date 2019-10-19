package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBookModel;
import seedu.address.model.AddressBookModelManager;

public class HelpCommandTest {
    private AddressBookModel addressBookModel = new AddressBookModelManager();
    private AddressBookModel expectedAddressBookModel = new AddressBookModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), addressBookModel, expectedCommandResult, expectedAddressBookModel);
    }
}
