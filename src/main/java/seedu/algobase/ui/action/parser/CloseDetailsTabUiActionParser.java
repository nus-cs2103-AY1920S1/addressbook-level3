package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.CloseDetailsTabUiAction;

/**
 * Parses input arguments and creates a new CloseDetailsTabUiAction object
 */
public class CloseDetailsTabUiActionParser implements UiParser<CloseDetailsTabUiAction> {

    private static final int MODEL_TYPE_INDEX = 0;
    private static final int ID_INDEX = 1;

    private static final Logger logger = LogsCenter.getLogger(CloseDetailsTabUiActionParser.class);

    @Override
    public CloseDetailsTabUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        assert uiActionDetails.size() == 2;
        assert uiActionDetails.get(MODEL_TYPE_INDEX) instanceof ModelType;
        assert uiActionDetails.get(ID_INDEX) instanceof Id;

        ModelType modelType = UiParserUtil.parseModelType(uiActionDetails.get(MODEL_TYPE_INDEX));
        Id id = UiParserUtil.parseId(uiActionDetails.get(ID_INDEX));
        return new CloseDetailsTabUiAction(modelType, id);
    }
}
