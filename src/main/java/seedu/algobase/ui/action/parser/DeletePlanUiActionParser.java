package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(DeletePlanUiActionParser.class);

    /**
     * Parses the given {@code UiActionDetails} object
     * and returns an DeletePlanUiAction object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePlanUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        assert uiActionDetails.size() == 1;
        assert uiActionDetails.get(ID_INDEX) instanceof Id;

        Id id = UiParserUtil.parseId(uiActionDetails.get(ID_INDEX));

        return new DeletePlanUiAction(id);
    }
}
