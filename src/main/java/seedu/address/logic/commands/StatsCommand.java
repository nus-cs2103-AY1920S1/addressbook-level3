package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.Statistics.StatisticsType;
import seedu.address.model.statistics.StatisticsList;
import seedu.address.model.statistics.exceptions.InvalidStatisticsType;

/**
 * Shows overall statistics gathered from the reviews of all the eateries in the application.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Presents a visual overview of all your eateries and reviews.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_STATS_ERROR = "Unable to generate your statistics.";
    public static final String MESSAGE_STATS_SUCCESS = "Currently generating your statistics.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Statistics stats = new Statistics(model.getAddressBook().getEateryList());
        StatisticsList statsList = new StatisticsList();

        try {
            statsList.addToStatisticsList(StatisticsType.GRAPH_OVERALL_CATEGORY_TOTAL,
                    stats.generate(StatisticsType.GRAPH_OVERALL_CATEGORY_TOTAL));
            statsList.addToStatisticsList(StatisticsType.GRAPH_OVERALL_CATEGORY_AVG,
                    stats.generate(StatisticsType.GRAPH_OVERALL_CATEGORY_AVG));

            model.setStatistics(statsList);
            return new CommandResult(MESSAGE_STATS_SUCCESS, false, false, false, true);

        } catch (InvalidStatisticsType s) {
            throw new CommandException(MESSAGE_STATS_ERROR);
        }
    }
}
