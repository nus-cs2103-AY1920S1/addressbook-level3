package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionDetails;

/**
 * Parses UI Actions.
 */
public class AlgoBaseUiActionParser {

    private static final Logger logger = LogsCenter.getLogger(AlgoBaseUiActionParser.class);

    /**
     * Parses user input into UI Action for execution.
     *
     * @param uiActionDetails uiActionDetails object.
     * @return the command based on the user input.
     * @throws ParseException if the uiAction does not conform the expected format
     */
    public UiAction parseCommand(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);
        requireNonNull(uiActionDetails.getActionWord());

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        switch (uiActionDetails.getActionWord()) {

        // Tabs
        case SWITCH_DISPLAY_TAB:
            return new SwitchDisplayTabUiActionParser().parse(uiActionDetails);
        case OPEN_DETAILS_TAB:
            return new OpenDetailsTabUiActionParser().parse(uiActionDetails);
        case CLOSE_DETAILS_TAB:
            return new CloseDetailsTabUiActionParser().parse(uiActionDetails);
        case SWITCH_DETAILS_TAB:
            return new SwitchDetailsTabUiActionParser().parse(uiActionDetails);

        // Problems
        case EDIT_PROBLEM:
            return new EditProblemUiActionParser().parse(uiActionDetails);
        case DELETE_PROBLEM:
            return new DeleteProblemUiActionParser().parse(uiActionDetails);

        // Plans
        case EDIT_PLAN:
            return new EditPlanUiActionParser().parse(uiActionDetails);
        case DELETE_PLAN:
            return new DeletePlanUiActionParser().parse(uiActionDetails);
        case SET_PLAN:
            return new SetPlanUiActionParser().parse(uiActionDetails);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION);
        }
    }
}
