package seedu.moolah.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;

import java.time.Period;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.statistics.Statistics;
import seedu.moolah.model.statistics.TabularStatistics;
import seedu.moolah.ui.statistics.StatsPanel;

/**
 * Calculates comparison statistics for MooLah. Creates a table that shows
 * the similarity of expenses in both periods and the difference of expenses in both periods
 */
public class StatsCompareCommand extends Command {
    public static final String COMMAND_WORD = "statscompare" + CommandGroup.GENERAL;

    public static final String MESSAGE_SUCCESS = "Statistics Comparison Calculated!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Compare statistics between two time periods\n"
            + "Parameters: "
            + "[" + PREFIX_FIRST_START_DATE + "START_DATE] "
            + "[" + PREFIX_SECOND_START_DATE + "END_DATE]\n "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FIRST_START_DATE + "11-11-1111 "
            + PREFIX_SECOND_START_DATE + "12-12-1212 ";

    private final Timestamp firstStartDate;

    private final Timestamp secondStartDate;

    public StatsCompareCommand(Timestamp date1, Timestamp date2) {
        requireNonNull(date1);
        requireNonNull(date2);

        this.firstStartDate = date1;
        this.secondStartDate = date2;
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasPrimaryBudget()) {
            throw new CommandException(MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        Budget primaryBudget = model.getPrimaryBudget();
        Statistics statistics = createTabularStatistics(primaryBudget);
        model.setStatistics(statistics);
        return new CommandResult(MESSAGE_SUCCESS, false, false, StatsPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsCompareCommand // instance of handles nulls
                && firstStartDate.equals(((StatsCompareCommand) other).firstStartDate)
                && secondStartDate.equals(((StatsCompareCommand) other).secondStartDate));
    }

    /**
     * Creates and returns a {@code Statistics} that constructs 2 intervals of duration
     * equal to {@code primaryBudget}, to compare their expenses
     */
    private Statistics createTabularStatistics(Budget primaryBudget) {
        requireNonNull(primaryBudget);

        Period period = primaryBudget.getBudgetPeriod().getPeriod();

        Timestamp firstEndDate = new Timestamp(firstStartDate.getFullTimestamp().plus(period)).minusDays(1);
        Timestamp secondEndDate = new Timestamp(secondStartDate.getFullTimestamp().plus(period)).minusDays(1);

        TabularStatistics statistics = new TabularStatistics(primaryBudget.getExpenses(),
                firstStartDate, firstEndDate,
                secondStartDate, secondEndDate);

        statistics.populateData();
        return statistics;
    }

}







