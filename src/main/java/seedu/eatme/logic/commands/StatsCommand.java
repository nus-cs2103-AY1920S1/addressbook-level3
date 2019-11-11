package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;

import seedu.eatme.model.statistics.Statistics;
import seedu.eatme.model.statistics.exceptions.CannotGenerateStatisticsException;
import seedu.eatme.model.statistics.exceptions.NoAvailableDataException;

/**
 * Shows overall statistics gathered from the reviews of all the eateries in the application.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Presents a visual overview of all your eateries and reviews.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_STATS_SUCCESS = "Currently generating your statistics.";
    public static final String MESSAGE_STATS_ERROR_WRONGMODE = "Can't execute statistics in Todo mode.";
    public static final String MESSAGE_STATS_ERROR_UNKNOWN = "Unable to generate statistics due to unknown error.";
    public static final String MESSAGE_STATS_ERROR_NODATA = "No available review data to create statistics currently.";

    public final boolean canExecute;

    public StatsCommand(boolean isMainMode) {
        this.canExecute = isMainMode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!canExecute) {
            throw new CommandException(MESSAGE_STATS_ERROR_WRONGMODE);
        }

        requireNonNull(model);

        try {
            Statistics stats = new Statistics(model.getEateryList().getEateryList());
            model.setStatistics(stats);
            return new CommandResult(MESSAGE_STATS_SUCCESS, false, false, false, true);

        } catch (NoAvailableDataException n) {
            throw new CommandException(MESSAGE_STATS_ERROR_NODATA);

        } catch (CannotGenerateStatisticsException c) {
            throw new CommandException(MESSAGE_STATS_ERROR_UNKNOWN);
        }
    }
}
