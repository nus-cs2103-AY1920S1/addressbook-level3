package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.SwitchDisplayTabUiAction;

/**
 * UI Parser for the Switch Display Tab Action.
 */
public class SwitchDisplayTabUiActionParser implements UiParser<SwitchDisplayTabUiAction> {

    private static final int INDEX_INDEX = 0;
    private static final Logger logger = LogsCenter.getLogger(SwitchDisplayTabUiActionParser.class);

    @Override
    public SwitchDisplayTabUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        assert uiActionDetails.size() == 1;
        assert uiActionDetails.get(INDEX_INDEX) instanceof Index;

        Index index = UiParserUtil.parseIndex(uiActionDetails.get(INDEX_INDEX));
        return new SwitchDisplayTabUiAction(index);
    }
}
