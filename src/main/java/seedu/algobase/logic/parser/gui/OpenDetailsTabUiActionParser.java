package seedu.algobase.logic.parser.gui;

import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.gui.OpenTabCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.UiAction;
import seedu.algobase.model.gui.UiActionDetails;

/**
 * UI Parser for the Open Details Tab Action.
 */
public class OpenDetailsTabUiActionParser implements UiParser<OpenTabCommand> {

    @Override
    public OpenTabCommand parse(UiAction uiAction) throws ParseException {
        UiActionDetails uiActionDetails = uiAction.getActionDetails();
        ModelType modelType = parseModelType(uiActionDetails.get(0));
        Index index = parseIndex(uiActionDetails.get(1));
        return new OpenTabCommand(modelType, index);
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
     * Converts an index of type {@Index} into an index of type {@Index}
     *
     * @throws ParseException if given object is not of type {@Index}
     */
    private Index parseIndex(Object index) throws ParseException {
        if (!(index instanceof Index)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Index) index;
    }
}
