package seedu.algobase.ui.action;

import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.ui.UiActionException;

/**
 * Logic for the UI Actions.
 */
public interface UiLogic {

    /**
     * Executes the UI Action and returns the result.
     * @param uiActionDetails The ui action details as input by the user.
     * @return the result of the command execution.
     * @throws UiActionException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    UiActionResult execute(UiActionDetails uiActionDetails) throws UiActionException, ParseException;
}
