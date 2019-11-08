package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.SwitchDetailsTabUiAction;

/**
 * Parses input arguments and creates a new SwitchDetailsTabUiAction object
 */
public class SwitchDetailsTabUiActionParser implements UiParser<SwitchDetailsTabUiAction> {

    private static final int INDEX_INDEX = 0;
    private final Logger logger = LogsCenter.getLogger(SwitchDetailsTabUiActionParser.class);

    @Override
    public SwitchDetailsTabUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        requireNonNull(uiActionDetails);
        assert uiActionDetails.size() == 1;
        assert uiActionDetails.get(INDEX_INDEX) instanceof Index;

        Index index = UiParserUtil.parseIndex(uiActionDetails.get(INDEX_INDEX));
        return new SwitchDetailsTabUiAction(index);
    }
}
