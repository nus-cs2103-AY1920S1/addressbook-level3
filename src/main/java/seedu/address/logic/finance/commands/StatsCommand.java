package seedu.address.logic.finance.commands;

import seedu.address.model.finance.Model;

/**
 * Display summary statistics of finance log entries.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows finance log's summary statistics.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Showing finance log statistics";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, true, false);
    }
}
