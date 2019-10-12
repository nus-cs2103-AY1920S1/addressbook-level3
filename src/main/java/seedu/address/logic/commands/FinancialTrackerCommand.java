package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Switch to financial tracker window command.
 */
public class FinancialTrackerCommand extends Command {

    public static final String COMMAND_WORD = "finance";

    public static final String MESSAGE_SUCCESS = "switched to Financial Tracker Window";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
