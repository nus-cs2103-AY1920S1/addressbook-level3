package seedu.algobase.logic.parser.gui;

import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION;

import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.gui.UiAction;

/**
 * Parses UI Actions.
 */
public class AlgoBaseUiActionParser {

    /**
     * Parses user input into UI Action for execution.
     *
     * @param uiAction uiAction object.
     * @return the command based on the user input.
     * @throws ParseException if the uiAction does not conform the expected format
     */
    public Command parseCommand(UiAction uiAction) throws ParseException {
        switch (uiAction.getActionWord()) {
        case OPEN_DETAILS_TAB:
            return new OpenDetailsTabUiActionParser().parse(uiAction);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION);
        }
    }
}
