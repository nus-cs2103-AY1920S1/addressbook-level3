package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.SetPlanUiAction;


/**
 * Parses input arguments and creates a new SetPlanUiAction object
 */
public class SetPlanUiActionParser implements UiParser<SetPlanUiAction> {

    private static final int ID_INDEX = 0;
    private static final Logger logger = LogsCenter.getLogger(SetPlanUiActionParser.class);

    @Override
    public SetPlanUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        assert uiActionDetails.size() == 1;
        assert uiActionDetails.get(ID_INDEX) instanceof Id;

        Id id = UiParserUtil.parseId(uiActionDetails.get(ID_INDEX));
        return new SetPlanUiAction(id);
    }
}
