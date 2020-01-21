package seedu.algobase.ui.action;

/**
 * Represents a function that can execute commands.
 */
@FunctionalInterface
public interface UiActionExecutor {
    /**
     * Executes the command and returns the result.
     *
     * @see UiLogic#execute(UiActionDetails)
     */
    UiActionResult execute(UiActionDetails uiActionDetails);
}
