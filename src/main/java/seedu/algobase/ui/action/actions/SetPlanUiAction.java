package seedu.algobase.ui.action.actions;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionResult;

/**
 * Set the plan to be displayed in TaskManagementPane.
 */
public class SetPlanUiAction extends UiAction {

    public static final String MESSAGE_SET_PLAN_FAILURE = "No such plan exists!";

    private final Id planId;

    public SetPlanUiAction(Id planId) {
        this.planId = planId;
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        requireNonNull(model);

        List<Plan> lastShownList = model.getFilteredPlanList();

        for (Plan plan : lastShownList) {
            if (plan.getId().equals(planId)) {
                model.setCurrentPlan(plan);
                return new UiActionResult(true, Optional.empty());
            }
        }

        throw new UiActionException(MESSAGE_SET_PLAN_FAILURE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPlanUiAction // instanceof handles nulls
                && planId.equals(((SetPlanUiAction) other).planId)); // state check
    }
}
