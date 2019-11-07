package seedu.address.logic.commands.statisticscommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.wordbankcommands.WordBankCommand;
import seedu.address.model.Model;

/**
 * Represents a command on word bank statistics.
 */
public abstract class WordBankStatisticsCommand extends WordBankCommand {
    public abstract CommandResult execute(Model model) throws CommandException;
}
