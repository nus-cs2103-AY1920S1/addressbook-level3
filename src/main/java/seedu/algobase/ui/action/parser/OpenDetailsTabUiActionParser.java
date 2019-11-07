package seedu.algobase.ui.action.parser;

import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.OpenDetailsTabUiAction;

/**
 * Parses input arguments and creates a new OpenDetailsTabUiAction object
 */
public class OpenDetailsTabUiActionParser implements UiParser<OpenDetailsTabUiAction> {

    @Override
    public OpenDetailsTabUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        ModelType modelType = parseModelType(uiActionDetails.get(0));
        Id id = parseId(uiActionDetails.get(1));
        return new OpenDetailsTabUiAction(modelType, id);
    }

    /**
     * Converts a modelType of type {@Object} into a modelType of type {@ModelType}
     *
     * @throws ParseException if given object is not of type {@ModelType}
     */
    private ModelType parseModelType(Object modelType) throws ParseException {
        if (!(modelType instanceof ModelType)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (ModelType) modelType;
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
