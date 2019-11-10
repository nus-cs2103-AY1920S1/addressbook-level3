package seedu.moolah.logic.commands.statistics;
import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.Optional;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.statistics.Statistics;
import seedu.moolah.model.statistics.TrendStatistics;
import seedu.moolah.ui.statistics.StatsPanel;

/**
 * Represents a StatsTrendCommand that is meant to output statistics using the visual
 * representation of a trend line
 */
public class StatsTrendCommand extends Command {

    public static final String COMMAND_WORD = "statstrend";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows statistics trends for regular periods between the Start Date and End Date. "
            + "Parameters: "
            + PREFIX_START_DATE + "START_DATE "
            + PREFIX_END_DATE + "END_DATE "
            + PREFIX_MODE + "CATEGORY_OR_BUDGET "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_START_DATE + "11-11-1111 "
            + PREFIX_END_DATE + "12-12-1212 "
            + PREFIX_MODE + "category";

    public static final String MESSAGE_SUCCESS = "Statistics Trend Calculated!";

    public static final int HALF_OF_PERIOD_NUMBER = TrendStatistics.INTERVAL_COUNT / 2;

    private StatsTrendDescriptor statsTrendDescriptor;

    /**
     * Creates a StatsTrendCommand
     * @param statsTrendDescriptor details to calculate statistics with
     */
    public StatsTrendCommand(StatsTrendDescriptor statsTrendDescriptor) {
        requireNonNull(statsTrendDescriptor);
        this.statsTrendDescriptor = statsTrendDescriptor;
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
        Statistics statistics = createTrendStatistics(primaryBudget, statsTrendDescriptor);
        model.setStatistics(statistics);
        return new CommandResult(MESSAGE_SUCCESS, false, false, StatsPanel.PANEL_NAME);
    }

    /**
     * Creates and returns a {@code Statistics} with the details of {@code statsTrendDescriptor}
     * and {@code primaryBudget} where necessary.
     */
    private Statistics createTrendStatistics(Budget primaryBudget, StatsTrendDescriptor statsTrendDescriptor) {
        requireNonNull(primaryBudget);
        Optional<Timestamp> startDate = statsTrendDescriptor.getStartDate();
        Optional<Timestamp> endDate = statsTrendDescriptor.getEndDate();

        boolean isStartPresent = startDate.isPresent();
        boolean isEndPresent = endDate.isPresent();


        if (!isStartPresent && !isEndPresent) {
            Timestamp centreDate = primaryBudget.getWindowStartDate();
            endDate = Optional.of(centreDate.createForwardTimestamp(primaryBudget.getBudgetPeriod(),
                    StatsTrendCommand.HALF_OF_PERIOD_NUMBER));
            startDate = Optional.of(centreDate.createBackwardTimestamp(primaryBudget.getBudgetPeriod(),
                    StatsTrendCommand.HALF_OF_PERIOD_NUMBER));
        } else if (isStartPresent && !isEndPresent) {
            endDate = Optional.of(startDate.get().createForwardTimestamp(primaryBudget.getBudgetPeriod(),
                    2 * StatsTrendCommand.HALF_OF_PERIOD_NUMBER));
        } else if (!isStartPresent) {
            startDate = Optional.of(endDate.get().createBackwardTimestamp(primaryBudget.getBudgetPeriod(),
                    2 * StatsTrendCommand.HALF_OF_PERIOD_NUMBER));
        }

        TrendStatistics statistics = new TrendStatistics(startDate.get(), endDate.get(),
                primaryBudget, statsTrendDescriptor.getMode());
        statistics.populateData();
        return statistics;
    }


    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsTrendCommand // instance of handles nulls
                && statsTrendDescriptor.equals(((StatsTrendCommand) other).statsTrendDescriptor));
    }
}


