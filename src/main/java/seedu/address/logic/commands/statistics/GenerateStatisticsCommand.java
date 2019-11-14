package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Generates statistics of upcoming events that have a lack of manpower
 * and other statistics in the form of charts.
 */
public class GenerateStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "generate_stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates statistics of events and employees."
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Statistics Generated";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_STATS);
        }
        return new CommandResult(MESSAGE_SUCCESS, "Statistics");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
