package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(DeleteProblemUiActionParser.class);

    /**
     * Parses the given {@code UiActionDetails} object
     * and returns an DeleteProblemUiAction object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProblemUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        assert uiActionDetails.size() == 2;
        assert uiActionDetails.get(ID_INDEX) instanceof Id;
        assert uiActionDetails.get(IS_FORCED_INDEX) instanceof Boolean;

        Id id = UiParserUtil.parseId(uiActionDetails.get(ID_INDEX));
        Boolean isForced = UiParserUtil.parseBoolean(uiActionDetails.get(IS_FORCED_INDEX));

        return new DeleteProblemUiAction(id, isForced);
    }
}
