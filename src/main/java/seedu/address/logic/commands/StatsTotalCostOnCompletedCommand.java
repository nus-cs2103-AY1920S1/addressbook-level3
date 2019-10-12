package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Calculate the total cost on successful orders.
 */
public class StatsTotalCostOnCompletedCommand extends Command {

    public static final String COMMAND_WORD = "generate-totalCostCompleted";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generated total cost!!.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_STATS_MESSAGE = "Opened Statistics window with total cost of completed orders.";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        return new CommandResult(SHOWING_STATS_MESSAGE, UiChange.STATS_TOTAL_COST);
    }
}

