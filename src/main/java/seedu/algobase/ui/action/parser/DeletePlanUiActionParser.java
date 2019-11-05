package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.DeletePlanUiAction;

/**
 * Parses input arguments and creates a new DeletePlanUiAction object
 */
public class DeletePlanUiActionParser implements UiParser<DeletePlanUiAction> {

    private static final int ID_INDEX = 0;

    /**
     * Parses the given {@code UiActionDetails} object
     * and returns an DeletePlanUiAction object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePlanUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        Id id = parseId(uiActionDetails.get(ID_INDEX));

        return new DeletePlanUiAction(id);
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
