package seedu.moolah.logic.commands.statistics;
import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_DISPLAY_STATISTICS_WITHOUT_BUDGET;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.statistics.Mode;
import seedu.moolah.model.statistics.TrendStatistics;
import seedu.moolah.ui.StatsPanel;

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
    private final boolean mode;

    //made public for testing purposes?
    public StatsTrendCommand(Timestamp date1, Timestamp date2, Mode mode) {
        requireNonNull(mode);

        this.startDate = date1;
        this.endDate = date2;
        this.mode = mode.isBudgetMode();
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
        model.calculateStatistics(COMMAND_WORD , startDate, endDate, mode);
        return new CommandResult(MESSAGE_SUCCESS, false, false, StatsPanel.PANEL_NAME);
    }

    /**
     * Creates a StatsTrendCommand that only contains a start date
     * @param startDate The start date
     * @param mode The mode specified by the user
     */
    public static StatsTrendCommand createOnlyWithStartDate(Timestamp startDate, Mode mode) {
        requireNonNull(startDate);
        return new StatsTrendCommand(startDate, null, mode);
    }

    /**
     * Creates a StatsTrendCommand that only contains an end date
     * @param endDate The end date
     * @param mode The mode specified by the user
     */
    public static StatsTrendCommand createOnlyWithEndDate(Timestamp endDate, Mode mode) {
        requireNonNull(endDate);
        return new StatsTrendCommand(null, endDate, mode);
    }

    /**
     * Creates a StatsTrendCommand that contains a start date and an end date
     * @param startDate The start date
     * @param endDate The end date
     * @param mode The mode specified by the user
     */
    public static StatsTrendCommand createWithBothDates(Timestamp startDate, Timestamp endDate, Mode mode) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(mode);
        return new StatsTrendCommand(startDate, endDate, mode);
    }

    /**
     * Creates a StatsTrendCommand that does not contain a start date or end date
     * @param mode The mode specified by the user
     */
    public static StatsTrendCommand createWithNoDate(Mode mode) {
        return new StatsTrendCommand(null, null, mode);
    }


    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof StatsTrendCommand // instance of handles nulls
                && startDate.equals(((StatsTrendCommand) other).startDate)
                && endDate.equals(((StatsTrendCommand) other).endDate));
    }
}


