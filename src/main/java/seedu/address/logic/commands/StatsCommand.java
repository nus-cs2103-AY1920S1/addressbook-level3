package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.exceptions.CannotGenerateStatistics;
import seedu.address.model.statistics.exceptions.NoAvailableData;

/**
 * Shows overall statistics gathered from the reviews of all the eateries in the application.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Presents a visual overview of all your eateries and reviews.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_STATS_SUCCESS = "Currently generating your statistics.";
    public static final String MESSAGE_STATS_ERROR = "Can't execute statistics in Todo mode.";

    public final boolean canExecute;

    public StatsCommand(boolean isMainMode) {
        this.canExecute = isMainMode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!canExecute) {
            throw new CommandException(MESSAGE_STATS_ERROR);
        }

        requireNonNull(model);

        try {
            Statistics stats = new Statistics(model.getAddressBook().getEateryList());
            model.setStatistics(stats);
            return new CommandResult(MESSAGE_STATS_SUCCESS, false, false, false, true);

        } catch (NoAvailableData n) {
            throw new CommandException(n.getMessage());

        } catch (CannotGenerateStatistics c) {
            throw new CommandException(c.getMessage());
        }
    }
}
