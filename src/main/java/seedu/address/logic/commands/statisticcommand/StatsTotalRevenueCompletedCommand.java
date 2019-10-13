package seedu.address.logic.commands.statisticcommand;

import seedu.address.commons.util.StatsPayload;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Calculate the total revenue on successful orders.
 */
public class StatsTotalRevenueCompletedCommand extends Command {
    public static final String COMMAND_WORD = "generate-totalRevenueCompleted";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generated Total Revenue!.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_STATS_MESSAGE =
            "Opened Statistics window with total revenue of completed orders.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StatsPayload statsPayload = new StatsPayload(StatisticType.DEFAULT_REVENUE);
        return new CommandResult(SHOWING_STATS_MESSAGE, statsPayload, UiChange.STATS);
    }
}
