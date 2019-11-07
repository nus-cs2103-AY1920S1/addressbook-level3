package seedu.algobase.ui.action.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.SetPlanUiAction;


/**
 * Parses input arguments and creates a new SetPlanUiAction object
 */
public class SetPlanUiActionParser implements UiParser<SetPlanUiAction> {

    @Override
    public SetPlanUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        Id id = parseId(uiActionDetails.get(0));
        return new SetPlanUiAction(id);
    }

    /**
     * Converts an id of type {@Object} into an id of type {@Id}
     *
     * @throws ParseException if given object is not of type {@Id}
     */
    private Id parseId(Object id) throws ParseException {
        if (!(id instanceof Id)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Id) id;
    }
}
