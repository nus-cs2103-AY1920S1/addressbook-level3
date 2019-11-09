package seedu.algobase.ui.action;

import seedu.algobase.model.Model;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.actions.CloseDetailsTabUiAction;
import seedu.algobase.ui.action.actions.DeletePlanUiAction;
import seedu.algobase.ui.action.actions.DeleteProblemUiAction;
import seedu.algobase.ui.action.actions.EditPlanUiAction;
import seedu.algobase.ui.action.actions.EditProblemUiAction;
import seedu.algobase.ui.action.actions.OpenDetailsTabUiAction;
import seedu.algobase.ui.action.actions.SetPlanUiAction;
import seedu.algobase.ui.action.actions.SwitchDetailsTabUiAction;

/**
 * A class representing details of actions that take place in the UI.
 */
public abstract class UiAction {
    public static final Class[] UI_ACTION_LIST = {
        OpenDetailsTabUiAction.class,
        CloseDetailsTabUiAction.class,
        SwitchDetailsTabUiAction.class,
        EditProblemUiAction.class,
        DeleteProblemUiAction.class,
        EditPlanUiAction.class,
        DeletePlanUiAction.class,
        SetPlanUiAction.class
    };

    /**
     * Executes the UI Action and optionally returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws UiActionException If an error occurs during UI Action execution.
     */
    public abstract UiActionResult execute(Model model) throws UiActionException;

}
