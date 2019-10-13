package seedu.address.logic.commands.statisticcommand;

import seedu.address.commons.util.StatsPayload;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Calculate the total profit on successful orders.
 */
public class StatsTotalProfitOnCompletedCommand extends Command {

    public static final String COMMAND_WORD = "generate-totalProfitCompleted";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generated Statistics!.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_STATS_MESSAGE = "Opened Statistics window with total "
            + "profit of completed orders.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StatsPayload statsPayload = new StatsPayload(StatisticType.DEFAULT_PROFIT);
        return new CommandResult(SHOWING_STATS_MESSAGE, statsPayload, UiChange.STATS);
    }
}
