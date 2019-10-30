package seedu.address.financialtracker.logic.commands;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * List out a summary of your current expenses.
 */
public class SummaryCommand extends Command<Model> {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_FAIL = "Unknown error";

    @Override
    public CommandResult execute(Model model) {

        return new CommandResult(MESSAGE_FAIL, false, false);
    }
}
