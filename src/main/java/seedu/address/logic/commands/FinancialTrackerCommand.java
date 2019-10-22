package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.address.logic.commands.Command;
import seedu.address.address.model.AddressBookModel;

/**
 * Switch to financial tracker window command.
 */
public class FinancialTrackerCommand extends Command {

    public static final String COMMAND_WORD = "financial_tracker";

    @Override
    public CommandResult execute(AddressBookModel addressBookModel) {
        requireNonNull(addressBookModel);
        return new CommandResult(COMMAND_WORD, false, false, true);
    }
}
