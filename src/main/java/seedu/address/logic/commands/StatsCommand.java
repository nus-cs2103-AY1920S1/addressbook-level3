package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.statistic.Statistic;

/**
 * generate Statistics from current Books
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "generate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generated Statistics!.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_STATS_MESSAGE = "Opened Statistics window.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    @Override
    public CommandResult execute(Statistic statistic) {
        statistic.calculateTotalCost();
        return new CommandResult(SHOWING_STATS_MESSAGE, false, false, true);
    }
}
