package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.moneygowhere.model.Model.PREDICATE_SHOW_ALL_SPENDINGS;

import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Displays statistics of the user's spending based on date range provided, if any.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Updates the statistics panel.\n"
        + "Parameters: "
        + PREFIX_DATE + "DATE_START and"
        + PREFIX_DATE + "DATE_END\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_DATE + "today "
        + PREFIX_DATE + "tomorrow ";

    public static final String MESSAGE_INVALID_DATERANGE = "Date range provided is invalid. "
        + "Only 2 dates, DATE_START and DATE_END are to be provided with "
        + "DATE_START being earlier or equal to DATE_END.\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_DATE + "today "
        + PREFIX_DATE + "tomorrow ";

    private String messageSuccess;

    private final Logger logger = Logger.getLogger("Statistics Logger");

    private Date startDate;
    private Date endDate;

    /**
     * Creates a StatsCommand with no parameters if date range is not specified.
     */
    public StatsCommand() {
        startDate = null;
        endDate = null;
        messageSuccess = "Statistics panel updated for all spending!\n"
            + "To view statistics for a more specific date range, type: stats d/DATE_START d/DATE_END.\n";
    }

    /**
     * Creates a StatsCommand with the specified date range.
     */
    public StatsCommand(Date startingDate, Date endingDate) {
        requireNonNull(startingDate);
        requireNonNull(endingDate);
        startDate = startingDate;
        endDate = endingDate;
        messageSuccess = "Statistics panel updated for spending between specified date range!\n";
    }

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredSpendingList(getStatsPredicate());
        logger.log(Level.INFO, String.format("Showing statistics from %s to %s", startDate, endDate));
        return new CommandResult(messageSuccess, false, true, false);
    }

    /**
     * Returns a predicate of the date range if a date range is specified.
     * Else, return a predicate to show all spending.
     */
    public Predicate<Spending> getStatsPredicate() {
        if (startDate == null && endDate == null) {
            return PREDICATE_SHOW_ALL_SPENDINGS;
        } else {
            return s-> {
                return s.getDate().compareTo(startDate) >= 0
                    && s.getDate().compareTo(endDate) <= 0;
            };
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            //Comparing null dates
            || (other instanceof StatsCommand // instanceof handles nulls
            && (startDate == ((StatsCommand) other).startDate)
            && endDate == ((StatsCommand) other).endDate)
            //Comparing valid dates
            || (other instanceof StatsCommand // instanceof handles nulls
            && (startDate.equals(((StatsCommand) other).startDate)
            && endDate.equals(((StatsCommand) other).endDate)));
    }
}
