package seedu.ifridge.logic.commands.wastelist;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ReadOnlyWasteList;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.model.waste.WasteStatistic;

/**
 * Provides feedback to the user about user's current food waste
 */
public class FeedbackWasteCommand extends Command {

    public static final String COMMAND_WORD = "feedback";

    public static final String MESSAGE_USAGE = "wlist " + COMMAND_WORD
            + ": Gives you feedback based on your current food waste performance for the month.\n"
            + "Example: wlist " + COMMAND_WORD;

    public static final String MESSAGE_CURRENT_WASTAGE = "Currently, you have wasted %.3f kg, %.3f litres, "
            + "and %d units of food.\n";

    public static final String MESSAGE_PREDICTED_WASTAGE = "Your predicted food wastage for this month is estimated to "
            + "be %.3f kg, %.3f litres, and %d units.";

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
        WasteMonth currentWasteMonth = new WasteMonth(LocalDate.now());
        ReadOnlyWasteList currentWasteList = model.getWasteListByMonth(currentWasteMonth);
        WasteStatistic currentWasteStatistic = currentWasteList.getWasteStatistic();

        List<ReadOnlyWasteList> pastWasteLists = new ArrayList<>();
        SortedSet<WasteMonth> pastWasteMonths = model.getDescendingWasteMonths();
        pastWasteMonths.remove(currentWasteMonth);
        int numWasteLists = 0;
        for (WasteMonth wm : pastWasteMonths) {
            pastWasteLists.add(model.getWasteListByMonth(wm));
            numWasteLists++;
            if (numWasteLists >= 3) {
                break;
            }
        }
        WasteStatistic predictedWastage = WasteStatistic.getWeightedStatistics(currentWasteList, pastWasteLists);

        String currentWastageMessage = String.format(MESSAGE_CURRENT_WASTAGE,
                currentWasteStatistic.getTotalWeight(),
                currentWasteStatistic.getTotalVolume(),
                (int) Math.ceil(currentWasteStatistic.getTotalQuantity()));

        String predictedWastageMessage = String.format(MESSAGE_PREDICTED_WASTAGE,
                predictedWastage.getTotalWeight(),
                predictedWastage.getTotalVolume(),
                (int) Math.ceil(predictedWastage.getTotalQuantity()));

        return new CommandResult(currentWastageMessage + predictedWastageMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof FeedbackWasteCommand;
    }
}
