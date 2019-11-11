package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.earnings.Earnings;

import java.util.List;

/**
 * Gets the total earnings of the user.
 */
public class TotalEarningsCommand extends Command {

    public static final String COMMAND_WORD = "total_earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gives you the total earnings you have earned. " + "\n"

            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Good Job! This is how much you have earned: $";
    public static final String MESSAGE_FAILURE =
            "Unable to get total earnings. Please try again.";

    public TotalEarningsCommand() {

    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Earnings> lastShownList = model.getFilteredEarningsList();

        if (Earnings.getTotalEarnings().equals("0.00")) {
            for (Earnings e : lastShownList) {
                Earnings.addToTotalEarnings(e.getAmount());
            }
        }

        return new CommandResult(MESSAGE_SUCCESS + Earnings.getTotalEarnings(),
                false, false, true, false, false,
                false, false, false);
    }
}
