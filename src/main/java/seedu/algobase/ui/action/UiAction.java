package seedu.algobase.ui.action;

import seedu.algobase.model.Model;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.actions.OpenTabUiAction;

/**
 * A class representing details of actions that take place in the UI.
 */
public abstract class UiAction {
    public static final Class[] UI_ACTION_LIST = {
        OpenTabUiAction.class,
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
