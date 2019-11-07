package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.DeleteProblemUiAction;

/**
 * Parses input arguments and creates a new DeleteProblemUiAction object
 */
public class DeleteProblemUiActionParser implements UiParser<DeleteProblemUiAction> {

    private static final int ID_INDEX = 0;
    private static final int IS_FORCED_INDEX = 1;

    /**
     * Parses the given {@code UiActionDetails} object
     * and returns an DeleteProblemUiAction object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProblemUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        Id id = parseId(uiActionDetails.get(ID_INDEX));
        Boolean isForced = parseBoolean(uiActionDetails.get(IS_FORCED_INDEX));

        return new DeleteProblemUiAction(id, isForced);
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

    /**
     * Converts a boolean of type {@Object} into a boolean of type {@Boolean}
     *
     * @throws ParseException if given object is not of type {@Boolean}
     */
    private Boolean parseBoolean(Object bool) throws ParseException {
        if (!(bool instanceof Boolean)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Boolean) bool;
    }
}
