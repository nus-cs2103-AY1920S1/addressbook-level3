package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travezy.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.travezy.address.logic.commands.HelpCommand;
import seedu.travezy.address.model.AddressBookModel;
import seedu.travezy.address.model.AddressBookModelManager;
import seedu.travezy.logic.commands.CommandResult;

public class HelpCommandTest {
    private AddressBookModel addressBookModel = new AddressBookModelManager();
    private AddressBookModel expectedAddressBookModel = new AddressBookModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), addressBookModel, expectedCommandResult, expectedAddressBookModel);
    }
}
