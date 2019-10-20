package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;

/**
 * Displays statistics of the user's spending.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_SUCCESS = "Statistics of all spending displayed below.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates statistics of all spendings within the date range specified "
        + "by the user input. If no user input is given, the date range will be the whole date range.\n"
        + "Parameters: startDate and endDate (endDate must be later or equal to startDate)\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_DATE + "today "
        + PREFIX_DATE + "tomorrow ";

    private Date startDate = null;
    private Date endDate = null;

    /**
     * Creates a StatsCommand with whole date range of all spending
     * if date range is not specified.
     */
    public StatsCommand() {

    }

    /**
     * Creates a StatsCommand with specified date range.
     */
    public StatsCommand(Date startingDate, Date endingDate) {
        requireNonNull(startingDate);
        requireNonNull(endingDate);
        startDate = startingDate;
        endDate = endingDate;

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Spending> lastShownList = model.getFilteredSpendingList();

        //Filters list based on date range if date range is specified.
        if (startDate != null && endDate != null) {
            lastShownList = model.getFilteredSpendingList().filtered(s-> {
                return s.getDate().value.compareTo(startDate.value) >= 0
                    && s.getDate().value.compareTo(endDate.value) <= 0;
            });
        }

        double totalCost = 0;
        for (Spending i: lastShownList) {
            totalCost += Double.parseDouble(i.getCost().toString());
        }

        double budget = model.getBudget().getValue();

        double budgetRemaining = budget - totalCost;

        String feedbackToUser;

        if (budgetRemaining >= 0) {
            String s = MESSAGE_SUCCESS
                + "\nTotal Cost: $" + String.format("%.2f", totalCost)
                + "\nBudget Set: $" + String.format("%.2f", budget)
                + "\nBudget Remaining: $" + String.format("%.2f", budgetRemaining)
                + "\nStatus: Surplus";
            feedbackToUser = s;
        } else {
            String s = MESSAGE_SUCCESS
                + "\nTotal Cost: $" + String.format("%.2f", totalCost)
                + "\nBudget Set: $" + String.format("%.2f", budget)
                + "\nBudget Remaining: -$" + String.format("%.2f", -1 * budgetRemaining)
                + "\nStatus: Deficit";
            feedbackToUser = s;
        }
        return new CommandResult(feedbackToUser);
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
