package seedu.address.financialtracker.commands;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.CommandResult;

/**
 * List out a summary of your current expenses.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_FAIL = "Unknown error, your expenses are not deleted.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_FAIL, false, false);
    }
}
