package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * generate Statistics from current Books
 */
public class StatsTotalProfitCommand extends Command {

    public static final String COMMAND_WORD = "generate-totalProfit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generated Statistics!.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_STATS_MESSAGE = "Opened Statistics window with total " +
            "profit of completed orders.";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        return new CommandResult(SHOWING_STATS_MESSAGE, UiChange.STATS_TOTAL_PROFIT);
    }
}
