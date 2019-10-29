package seedu.address.address.logic.commands;

import seedu.address.address.model.AddressBookModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command<AddressBookModel> {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(AddressBookModel addressBookModel) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
