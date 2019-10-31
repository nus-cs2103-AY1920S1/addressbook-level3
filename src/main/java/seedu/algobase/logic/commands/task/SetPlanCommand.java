package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;

/**
 * Set the plan to be displayed in TaskManagementPane.
 */
public class SetPlanCommand extends Command {

    public static final String COMMAND_WORD = "setplan";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set as current plan the Plan identified by the index number used in the displayed Plan list.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + "Example:\n"
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_SET_PLAN_SUCCESS = "Plan [%1$s] set as current plan.";

    private final Index targetIndex;

    public SetPlanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Plan> lastShownList = model.getFilteredPlanList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }
        Plan plan = lastShownList.get(targetIndex.getZeroBased());
        model.setCurrentPlan(plan);

        return new CommandResult(String.format(MESSAGE_SET_PLAN_SUCCESS, plan.getPlanName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPlanCommand // instanceof handles nulls
                && targetIndex.equals(((SetPlanCommand) other).targetIndex)); // state check
    }
}
