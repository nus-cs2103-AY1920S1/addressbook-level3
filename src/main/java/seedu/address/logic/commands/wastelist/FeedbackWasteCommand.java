package seedu.address.logic.commands.wastelist;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.food.Amount.UNIT_KILOGRAM;
import static seedu.address.model.food.Amount.UNIT_LITRE;
import static seedu.address.model.food.Amount.UNIT_QUANTITY;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.WasteList;
import seedu.address.model.waste.WasteStatistic;

/**
 * Provides feedback to the user about user's current food waste
 */
public class FeedbackWasteCommand extends Command {

    public static final String COMMAND_WORD = "feedback";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Gives you feedback based on your current food waste performance for the month.\n"
            + "Example: wlist " + COMMAND_WORD;

    private static final String MESSAGE_SUCCESS = "Successfully displayed feedback results";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        WasteStatistic predictedWastage = WasteList.getCurrentMonthPredictedWasteStatistic();
        String feedbackMessage = "This month, your predicted food wastage will be \n"
                + predictedWastage.getTotalWeight() + UNIT_KILOGRAM + ", "
                + predictedWastage.getTotalVolume() + UNIT_LITRE + ", "
                + predictedWastage.getTotalQuantity() + UNIT_QUANTITY;
        /*
        //Give feedback based on current month's waste
        model.
        1. Get the current waste list
        2. Compute current food waste, predicted food waste
        3. Compare with last month's statistics, remark if there is an increase or decrease in food waste.
         */
        return new CommandResult(feedbackMessage);
    }
}
