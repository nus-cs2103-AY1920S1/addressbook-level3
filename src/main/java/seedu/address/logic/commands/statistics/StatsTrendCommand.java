package seedu.address.logic.commands.statistics;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.budget.BudgetPeriod;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.statistics.Mode;
import seedu.address.model.statistics.TrendStatistics;
import seedu.address.ui.StatsPanel;

/**
 * Calculates and displays statistics
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

    private final Timestamp startDate;
    private final Timestamp endDate;
    private final BudgetPeriod period;
    private final boolean mode;


    public StatsTrendCommand(Timestamp date1, Timestamp date2, BudgetPeriod period, Mode mode) {
        requireNonNull(date1);
        requireNonNull(date2);
        requireNonNull(period);
        requireNonNull(mode);

        this.startDate = date1;
        this.endDate = date2;
        this.period = period;
        this.mode = mode.isBudgetMode();
    }

    @Override
    protected void validate(Model model) {
        requireNonNull(model);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.calculateStatistics(COMMAND_WORD , startDate, endDate, period, mode);
        return new CommandResult(MESSAGE_SUCCESS, false, false, StatsPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsTrendCommand // instance of handles nulls
                && startDate.equals(((StatsTrendCommand) other).startDate)
                && endDate.equals(((StatsTrendCommand) other).endDate)
                && period.equals(((StatsTrendCommand) other).period));
    }
}


