package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.EditPlanUiAction;
import seedu.algobase.ui.action.actions.EditPlanUiAction.EditPlanDescriptor;

/**
 * Parses input arguments and creates a new EditPlanUiAction object
 */
public class EditPlanUiActionParser implements UiParser<EditPlanUiAction> {

    private static final int ID_INDEX = 0;
    private static final int PLAN_NAME_INDEX = 1;
    private static final int PLAN_DESCRIPTION_INDEX = 2;
    private static final int START_DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;

    private static final Logger logger = LogsCenter.getLogger(EditPlanUiActionParser.class);

    /**
     * Parses the given {@code UiActionDetails} object
     * and returns an EditPlanUiAction object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPlanUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        assert uiActionDetails.size() == 5;
        assert uiActionDetails.get(ID_INDEX) instanceof Id;
        assert uiActionDetails.get(PLAN_NAME_INDEX) instanceof String;
        assert uiActionDetails.get(PLAN_DESCRIPTION_INDEX) instanceof String;
        assert uiActionDetails.get(START_DATE_INDEX) instanceof LocalDate;
        assert uiActionDetails.get(END_DATE_INDEX) instanceof LocalDate;

        Id id = UiParserUtil.parseId(uiActionDetails.get(ID_INDEX));

        EditPlanDescriptor editPlanDescriptor = new EditPlanDescriptor();

        String planNameString = UiParserUtil.parseString(uiActionDetails.get(PLAN_NAME_INDEX));
        if (!planNameString.isBlank()) {
            editPlanDescriptor.setPlanName(ParserUtil.parsePlanName(planNameString));
        }

        String planDescriptionString = UiParserUtil.parseString(uiActionDetails.get(PLAN_DESCRIPTION_INDEX));
        if (!planDescriptionString.isBlank()) {
            editPlanDescriptor.setPlanDescription((ParserUtil.parsePlanDescription(planDescriptionString)));
        }

        LocalDate startDate = UiParserUtil.parseDate(uiActionDetails.get(START_DATE_INDEX));
        editPlanDescriptor.setStartDate(startDate);

        LocalDate endDate = UiParserUtil.parseDate(uiActionDetails.get(END_DATE_INDEX));
        editPlanDescriptor.setEndDate(endDate);

        if (!editPlanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPlanUiAction.MESSAGE_NOT_EDITED);
        }

        return new EditPlanUiAction(id, editPlanDescriptor);
    }
}
