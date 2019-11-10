package seedu.moolah.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Optional;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.statistics.PieChartStatistics;
import seedu.moolah.model.statistics.Statistics;
import seedu.moolah.ui.StatsPanel;

/**
 * Represents a StatsCommand that is meant to output statistics using the visual
 * representation of a pie chart
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "statsbasic" + CommandGroup.GENERAL;

    public static final String MESSAGE_SUCCESS = "Pie Chart calculated!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Calculates statistics between the Start Date and End Date "
            + "Parameters: "
            + "[" + PREFIX_START_DATE + "START_DATE] "
            + "[" + PREFIX_END_DATE + "END_DATE] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_START_DATE + "11-11-1111 "
            + PREFIX_END_DATE + "12-12-1212 ";


    private StatsDescriptor statsDescriptor;

    /**
     * Creates a StatsCommand to calculate statistics between 2 dates {@code Timestamp}
     */
    public StatsCommand(StatsDescriptor statsDescriptor) {
        requireNonNull(statsDescriptor);
        this.statsDescriptor = statsDescriptor;
    }


    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasPrimaryBudget()) {
            throw new CommandException(MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET);
        }
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Budget primaryBudget = model.getPrimaryBudget();
        Statistics statistics = createPieChartStatistics(primaryBudget, statsDescriptor);
        model.setStatistics(statistics);

        return new CommandResult(MESSAGE_SUCCESS, false, false, StatsPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsCommand // instance of handles nulls
                && statsDescriptor.equals(((StatsCommand) other).statsDescriptor));
    }

    /**
     * Creates and returns a {@code Statistics} with the details of {@code statsDescriptor}
     * and {@code primaryBudget} where necessary.
     */
    private Statistics createPieChartStatistics(Budget primaryBudget, StatsDescriptor statsDescriptor) {
        requireNonNull(primaryBudget);
        Optional<Timestamp> startDate = statsDescriptor.getStartDate();
        Optional<Timestamp> endDate = statsDescriptor.getEndDate();

        boolean isStartPresent = startDate.isPresent();
        boolean isEndPresent = endDate.isPresent();


        if (!isStartPresent && !isEndPresent) {
            startDate = Optional.of(primaryBudget.getWindowStartDate());
            endDate = Optional.of(primaryBudget.getWindowEndDate());
        } else if (isStartPresent && !isEndPresent) {
            endDate = Optional.of(startDate.get().createForwardTimestamp(primaryBudget.getBudgetPeriod()).minusDays(1));
        } else if (!isStartPresent) {
            startDate = Optional.of(endDate.get().createBackwardTimestamp(primaryBudget.getBudgetPeriod()).plusDays(1));
        }

        PieChartStatistics statistics = new PieChartStatistics(primaryBudget.getExpenses(),
                startDate.get(), endDate.get());
        statistics.populateData();
        return statistics;

    }


}


