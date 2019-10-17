package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.spending.Spending;

/**
 * Displays statistics of the user's spending.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_SUCCESS = "Statistics of all spending displayed below.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Spending> lastShownList = model.getFilteredSpendingList();

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
}
