package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Switch to financial tracker window command.
 */
public class FinancialTrackerCommand extends Command {

    public static final String COMMAND_WORD = "financial_tracker";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(COMMAND_WORD, false, false, true);
    }
}
