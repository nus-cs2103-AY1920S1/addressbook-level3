package seedu.algobase.ui.action.actions;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionResult;

/**
 * Deletes an existing Plan in the algobase.
 */
public class DeletePlanUiAction extends UiAction {

    public static final String MESSAGE_DELETE_PLAN_SUCCESS = "Plan [%1$s] deleted.";

    private final Id id;

    /**
     * @param id of the Plan in the filtered Plan list to delete
     */
    public DeletePlanUiAction(Id id) {
        requireNonNull(id);

        this.id = id;
    }


    /**
     * Retrieves the plan to be deleted from the problem list.
     */
    private Plan retrievePlanToDelete(List<Plan> planList) throws NoSuchElementException {
        for (Plan plan : planList) {
            if (plan.getId().equals(id)) {
                return plan;
            }
        }
        throw new NoSuchElementException("No such Plan exists");
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        requireNonNull(model);
        List<Plan> lastShownList = model.getFilteredPlanList();

        Plan planToDelete = retrievePlanToDelete(lastShownList);

        model.deletePlan(planToDelete);

        return new UiActionResult(
            true,
            Optional.of(String.format(MESSAGE_DELETE_PLAN_SUCCESS, planToDelete.getPlanName()))
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePlanUiAction)) {
            return false;
        }

        // state check
        DeletePlanUiAction e = (DeletePlanUiAction) other;
        return id.equals(e.id);
    }
}
