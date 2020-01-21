package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;


/**
 * Deletes a Plan from AlgoBase.
 */
public class DeletePlanCommand extends Command {

    public static final String COMMAND_WORD = "deleteplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Plan identified by the index number used in the displayed Plan list.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + "Example:\n"
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PLAN_SUCCESS = "Plan [%1$s] deleted.";

    private final Index targetIndex;

    public DeletePlanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownList = model.getFilteredPlanList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }

        Plan planToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePlan(planToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PLAN_SUCCESS, planToDelete.getPlanName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePlanCommand // instanceof handles nulls
                && targetIndex.equals(((DeletePlanCommand) other).targetIndex)); // state check
    }
}
