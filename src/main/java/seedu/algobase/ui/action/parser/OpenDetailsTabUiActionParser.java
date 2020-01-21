package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
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

    private static final int MODEL_TYPE_INDEX = 0;
    private static final int ID_INDEX = 1;
    private static final Logger logger = LogsCenter.getLogger(OpenDetailsTabUiActionParser.class);

    @Override
    public OpenDetailsTabUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        assert uiActionDetails.size() == 2;
        assert uiActionDetails.get(MODEL_TYPE_INDEX) instanceof ModelType;
        assert uiActionDetails.get(ID_INDEX) instanceof Id;

        ModelType modelType = UiParserUtil.parseModelType(uiActionDetails.get(MODEL_TYPE_INDEX));
        Id id = UiParserUtil.parseId(uiActionDetails.get(ID_INDEX));
        return new OpenDetailsTabUiAction(modelType, id);
    }
}
