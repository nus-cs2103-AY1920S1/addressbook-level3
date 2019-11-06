package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

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
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, "Statistics");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
