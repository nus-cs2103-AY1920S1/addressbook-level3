package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import java.time.LocalDate;

import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.EditPlanUiAction;
import seedu.algobase.ui.action.actions.EditPlanUiAction.EditPlanDescriptor;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditPlanUiActionParser implements UiParser<EditPlanUiAction> {

    private static final int ID_INDEX = 0;
    private static final int PLAN_NAME_INDEX = 1;
    private static final int PLAN_DESCRIPTION_INDEX = 2;
    private static final int START_DATE_INDEX = 3;
    private static final int END_DATE_INDEX = 4;

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPlanUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        Id id = parseId(uiActionDetails.get(ID_INDEX));

        EditPlanDescriptor editPlanDescriptor = new EditPlanDescriptor();

        String planNameString = parseString(uiActionDetails.get(PLAN_NAME_INDEX));
        if (!planNameString.isBlank()) {
            editPlanDescriptor.setPlanName(ParserUtil.parsePlanName(planNameString));
        }

        String planDescriptionString = parseString(uiActionDetails.get(PLAN_DESCRIPTION_INDEX));
        if (!planDescriptionString.isBlank()) {
            editPlanDescriptor.setPlanDescription((ParserUtil.parsePlanDescription(planDescriptionString)));
        }

        LocalDate startDate = parseDate(uiActionDetails.get(START_DATE_INDEX));
        editPlanDescriptor.setStartDate(startDate);

        LocalDate endDate = parseDate(uiActionDetails.get(END_DATE_INDEX));
        editPlanDescriptor.setEndDate(endDate);

        if (!editPlanDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPlanUiAction.MESSAGE_NOT_EDITED);
        }

        return new EditPlanUiAction(id, editPlanDescriptor);
    }

    /**
     * Converts an id of type {@Id} into an id of type {@Id}
     *
     * @throws ParseException if given object is not of type {@Id}
     */
    private Id parseId(Object id) throws ParseException {
        if (!(id instanceof Id)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Id) id;
    }

    /**
     * Converts an date of type {@Object} into a date of type {@LocalDate}
     *
     * @throws ParseException if given object is not of type {@LocalDate}
     */
    private LocalDate parseDate(Object date) throws ParseException {
        if (!(date instanceof LocalDate)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (LocalDate) date;
    }

    /**
     * Converts a string of type {@Object} into an string of type {@String}
     *
     * @throws ParseException if given object is not of type {@String}
     */
    private String parseString(Object string) throws ParseException {
        if (!(string instanceof String)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return ((String) string).trim();
    }
}
