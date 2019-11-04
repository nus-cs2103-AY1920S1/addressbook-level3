package seedu.algobase.ui.action.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION;

import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionDetails;

/**
 * Parses UI Actions.
 */
public class AlgoBaseUiActionParser {

    /**
     * Parses user input into UI Action for execution.
     *
     * @param uiActionDetails uiActionDetails object.
     * @return the command based on the user input.
     * @throws ParseException if the uiAction does not conform the expected format
     */
    public UiAction parseCommand(UiActionDetails uiActionDetails) throws ParseException {
        switch (uiActionDetails.getActionWord()) {
        case SWITCH_DISPLAY_TAB:
            return new SwitchDisplayTabUiActionParser().parse(uiActionDetails);
        case OPEN_DETAILS_TAB:
            return new OpenDetailsTabUiActionParser().parse(uiActionDetails);
        case CLOSE_DETAILS_TAB:
            return new CloseDetailsTabUiActionParser().parse(uiActionDetails);
        case SWITCH_DETAILS_TAB:
            return new SwitchDetailsTabUiActionParser().parse(uiActionDetails);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION);
        }
    }
}
