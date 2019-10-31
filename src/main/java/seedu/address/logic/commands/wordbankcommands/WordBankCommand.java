// @@author sreesubbash
package seedu.address.logic.commands.wordbankcommands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command executed on banks.
 */
public abstract class WordBankCommand extends Command {
    public abstract CommandResult execute(Model model) throws CommandException;
}
