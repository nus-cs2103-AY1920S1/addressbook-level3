package seedu.exercise.logic.commands.statistic;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CHART;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.exercise.logic.commands.Command;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.resource.Exercise;

/**
 * Generate statistic with given parameters.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_STATS_DISPLAY_SUCCESS = "Chart displayed.";

    public static final String MESSAGE_USAGE = "Parameters: "
        + PREFIX_CATEGORY + "CATEGORY "
        + PREFIX_CHART + "CHART TYPE "
        + PREFIX_START_DATE + "START DATE "
        + PREFIX_END_DATE + "END DATE " + "\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_CATEGORY + "calories "
        + PREFIX_CHART + "barchart "
        + PREFIX_START_DATE + "30/03/2019 "
        + PREFIX_END_DATE + "05/04/2019 ";

    private final String chart;
    private final String category;
    private final Date startDate;
    private final Date endDate;

    /**
     * Creates a StatsCommand to generate statistic.
     */
    public StatsCommand(String chart, String category, Date startDate, Date endDate) {
        this.chart = chart;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ReadOnlyResourceBook<Exercise> exercises = model.getExerciseBookData();
        StatsFactory statsFactory = new StatsFactory(exercises, chart, category, startDate, endDate);
        Statistic statistic = statsFactory.generateStatistic();
        model.setStatistic(statistic);
        return new CommandResult(MESSAGE_STATS_DISPLAY_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof StatsCommand) {
            if (this.startDate == null && this.endDate == null) {
                return this.category.equals(((StatsCommand) other).category)
                        && this.chart.equals(((StatsCommand) other).chart)
                        && ((StatsCommand) other).startDate == null
                        && ((StatsCommand) other).endDate == null;
            } else {
                return this.category.equals(((StatsCommand) other).category)
                        && this.chart.equals(((StatsCommand) other).chart)
                        && this.startDate.equals(((StatsCommand) other).startDate)
                        && this.endDate.equals(((StatsCommand) other).endDate);
            }
        }

        return false;
    }
}
