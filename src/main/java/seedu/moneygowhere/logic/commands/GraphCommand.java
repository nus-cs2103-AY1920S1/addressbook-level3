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
 * Format full help instructions for every command for display.
 */
public class GraphCommand extends Command {

    public static final String COMMAND_WORD = "graph";

    public static final String MESSAGE_SUCCESS = "Successfully updated the graph panel.\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Updates the graph panel.\n"
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
        + PREFIX_DATE + "tomorrow ";;

    private final Logger logger = Logger.getLogger("Graph Logger");

    private Date startDate;
    private Date endDate;

    /**
     * Creates a GraphCommand with whole date range of all spending
     * if date range is not specified.
     */
    public GraphCommand() {
        startDate = null;
        endDate = null;
    }

    /**
     * Creates a GraphCommand with specified date range.
     */
    public GraphCommand(Date startingDate, Date endingDate) {
        requireNonNull(startingDate);
        requireNonNull(endingDate);
        startDate = startingDate;
        endDate = endingDate;
    }

    @Override
    public CommandResult execute(Model model) {
        //To reset the spending list to default after previous commands
        model.updateFilteredSpendingList(PREDICATE_SHOW_ALL_SPENDINGS);
        model.updateStatsPredicate(getGraphPredicate());
        logger.log(Level.INFO, String.format("Showing statistics from %s to %s", startDate, endDate));
        return new CommandResult(MESSAGE_SUCCESS, true, false, false);
    }

    public Predicate<Spending> getGraphPredicate() {
        if (startDate == null && endDate == null) {
            return PREDICATE_SHOW_ALL_SPENDINGS;
        } else {
            return s-> {
                return s.getDate().value.compareTo(startDate.value) >= 0
                    && s.getDate().value.compareTo(endDate.value) <= 0;
            };
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            //Comparing null dates
            || (other instanceof GraphCommand // instanceof handles nulls
            && (startDate == ((GraphCommand) other).startDate)
            && endDate == ((GraphCommand) other).endDate)
            //Comparing valid dates
            || (other instanceof GraphCommand // instanceof handles nulls
            && (startDate.equals(((GraphCommand) other).startDate)
            && endDate.equals(((GraphCommand) other).endDate)));
    }
}
