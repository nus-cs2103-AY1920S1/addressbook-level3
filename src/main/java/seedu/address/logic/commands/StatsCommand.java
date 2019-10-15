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
            totalCost += Double.parseDouble(i.getPhone().toString());
        }

        return new CommandResult(MESSAGE_SUCCESS + "\nTotal Cost: $" + String.format("%.2f", totalCost));
    }
}
